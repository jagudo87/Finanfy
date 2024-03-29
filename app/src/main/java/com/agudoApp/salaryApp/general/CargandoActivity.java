package com.agudoApp.salaryApp.general;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.agudoApp.salaryApp.R;
import com.agudoApp.salaryApp.database.GestionBBDD;
import com.android.vending.billing.IInAppBillingService;

import org.json.JSONObject;

import java.util.ArrayList;

public class CargandoActivity extends Activity {
    private final String BD_NOMBRE = "BDGestionGastos";
    final GestionBBDD gestion = new GestionBBDD();
    boolean tablasCreadas = false;
    boolean tablasTarjetaCreadas = false;
    boolean tablasCuentasCreadas = false;
    boolean comprobarVersion30 = false;
    boolean comprobarVersion40 = false;
    private SQLiteDatabase db;

    // Podructos integrados
    // id de los productos
    static final String SKU_COMPRA_PREMIUM = "version_premium";
    static final String SKU_CATEGORIAS_PREMIUM = "categorias_premium";
    static final String SKU_SIN_PUBLICIDAD = "sin_publicidad";

    // Productos que posee el usuario
    boolean isPremium = false;
    boolean isCategoriaPremium = false;
    boolean isSinPublicidad = false;

    IInAppBillingService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargando);

        Intent serviceIntent = new Intent(
                "com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        // Asignamos el tipo de fuente
        Typeface miPropiaTypeFace = Typeface.createFromAsset(getAssets(),
                "fonts/Berlin.ttf");

//		TextView txtCargando = (TextView) findViewById(R.id.textCargando);
//		txtCargando.setTypeface(miPropiaTypeFace);

        if (!isPremium && gestion.existeBDPro()) {
            isPremium = true;
        }
    }

    public void iniciarApp() {

        // Se crea o abre la BD
        db = this.openOrCreateDatabase(BD_NOMBRE, 1, null);
        if (db != null) {
            // informar que se va a hacer

            // Comprobamos si existen las tablas
            tablasCreadas = gestion.comprobarTablas(db);
            tablasTarjetaCreadas = gestion.comprobarTablasTarjeta(db);

            // Comprobamos si existen las tablas cuentas
            tablasCuentasCreadas = gestion.comprobarTablasCuentas(db);

            // Comprobamos si tiene los cambios de la nueva version de la BD
            comprobarVersion30 = gestion.comprobarVersion30(db);

            // Comprobamos si tiene los cambios de la nueva version de la BD
            comprobarVersion40 = gestion.comprobarVersion40(db);

            if (!tablasCreadas) {
                gestion.createTables(db);
            } else if (tablasCreadas && !tablasCuentasCreadas) {
                gestion.actualizarBDCuentas(db);
            }
            if (tablasCreadas && !comprobarVersion30) {
                gestion.actualizarVersion30(db);
            }
            if (tablasCreadas && !comprobarVersion40) {
                gestion.actualizarVersion40(db);
            }
        }
        db.close();

        ArrayList<String> skuList = new ArrayList<String>();
        skuList.add(SKU_COMPRA_PREMIUM);
        skuList.add(SKU_CATEGORIAS_PREMIUM);
        skuList.add(SKU_SIN_PUBLICIDAD);

        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        Bundle ownedItems;
        try {
            ownedItems = mService.getPurchases(3, getPackageName(), "inapp",
                    null);
            int response = ownedItems.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> ownedSkus = ownedItems
                        .getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> purchaseDataList = ownedItems
                        .getStringArrayList("INAPP_PURCHASE_DATA_LIST");

                for (int i = 0; i < purchaseDataList.size(); ++i) {
                    String sku = (String) ownedSkus.get(i);

                    String purchase = purchaseDataList.get(i);

                    try {
                        JSONObject o = new JSONObject(purchase);
                        String orderId = o.getString("orderId");
                        String purchaseToken = o.getString("purchaseToken");

                        //boolean result = consumirProductos("1", orderId, purchaseToken);
                    } catch (Exception e) {
                    }

                    if (sku.equals(SKU_COMPRA_PREMIUM)) {
                        isPremium = true;
                    }

                    if (sku.equals(SKU_CATEGORIAS_PREMIUM)) {
                        isCategoriaPremium = true;
                    }

                    if (sku.equals(SKU_SIN_PUBLICIDAD)) {
                        isSinPublicidad = true;
                    }
                }

            }
        } catch (RemoteException e1) {
            return;
        }

        if (!isPremium && gestion.existeBDPro()) {
            isPremium = true;
        }

        if (isSinPublicidad && isCategoriaPremium) {
            isPremium = true;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class MyLoadingAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            iniciarApp();
            return null;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Void result) {
            Intent intent = new Intent(CargandoActivity.this,
                    FinanfyActivity.class);
            intent.putExtra("isPremium", isPremium);
            intent.putExtra("isSinPublicidad", isSinPublicidad);
            intent.putExtra("isCategoriaPremium", isCategoriaPremium);
            startActivity(intent);
            finish();
        }

    }

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            new MyLoadingAsyncTask().execute();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mServiceConn != null) {
            unbindService(mServiceConn);
        }
    }

    public boolean consumirProductos(String nAsesoramientos, String orderId,
                                     String purchaseToken) {
        // Guardamos en base de datos los asesoramientos adquiridos

        try {
            // Se consume el producto integrado
            int response;

            response = mService.consumePurchase(3, getPackageName(),
                    purchaseToken);

            if (response == 0) {
                return true;
            } else {
                return false;
            }

            }catch(Exception e){
                // TODO Auto-generated catch block
                return false;
            }
        }
    }
