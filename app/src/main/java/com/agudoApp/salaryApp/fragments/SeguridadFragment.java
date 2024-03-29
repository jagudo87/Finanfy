package com.agudoApp.salaryApp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.agudoApp.salaryApp.R;
import com.agudoApp.salaryApp.activities.SeguridadAntigua;
import com.agudoApp.salaryApp.activities.SeguridadComprobar;
import com.agudoApp.salaryApp.activities.SeguridadIntroducir;
import com.agudoApp.salaryApp.database.GestionBBDD;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SeguridadFragment extends Fragment {
	private static final String KEY_CONTENT = "SeguridadFragment:Content";
	final GestionBBDD gestion = new GestionBBDD();
	private LinearLayout layoutActivar;
	private LinearLayout layoutDesactivar;
	private LinearLayout layoutModificar;
	boolean seguridadAct;

	SharedPreferences prefs;
	SharedPreferences.Editor editor;

	private RelativeLayout layoutPubli;

	// Productos que posee el usuario
	boolean isPremium = false;
	boolean isCategoriaPremium = false;
	boolean isSinPublicidad = false;

	private String mContent = "???";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		Bundle bundle = getArguments();
		isPremium = bundle.getBoolean("isPremium");
		isCategoriaPremium = bundle.getBoolean("isCategoriaPremium");
		isSinPublicidad = bundle.getBoolean("isSinPublicidad");

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.menu_seguridad, container, false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		//((FinanfyActivity)getActivity()).mostrarPublicidad(true, false);

		prefs = getActivity().getSharedPreferences("ficheroConf",
				Context.MODE_PRIVATE);
		editor = prefs.edit();

		seguridadAct = prefs.getBoolean("seguridadActivada", false);

		layoutActivar = (LinearLayout) getView().findViewById(
				R.id.layoutActivar);
		layoutDesactivar = (LinearLayout) getView().findViewById(
				R.id.layoutDesactivar);
		layoutModificar = (LinearLayout) getView().findViewById(
				R.id.layoutModificar);

		layoutPubli = (RelativeLayout) getView().findViewById(R.id.layoutPubli);

		//Se carga la publicidad
		AdView adView = (AdView) getView().findViewById(R.id.adView);
		if (isPremium || isSinPublicidad) {
			layoutPubli.setVisibility(View.GONE);
		} else {
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);
		}

		if (seguridadAct) {
			layoutActivar.setVisibility(View.GONE);
			layoutDesactivar.setVisibility(View.VISIBLE);
			layoutModificar.setVisibility(View.VISIBLE);
		} else {
			layoutActivar.setVisibility(View.VISIBLE);
			layoutDesactivar.setVisibility(View.GONE);
			layoutModificar.setVisibility(View.GONE);
		}

		layoutActivar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SeguridadIntroducir.class);
				intent.putExtra("isPremium", isPremium);
				intent.putExtra("isSinPublicidad", isSinPublicidad);
				startActivity(intent);
			}
		});

		layoutDesactivar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SeguridadComprobar.class);
				intent.putExtra("funcionalidad", "desactivar");
				intent.putExtra("isPremium", isPremium);
				intent.putExtra("isSinPublicidad", isSinPublicidad);
				startActivity(intent);
			}
		});

		layoutModificar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SeguridadAntigua.class);
				intent.putExtra("isPremium", isPremium);
				intent.putExtra("isSinPublicidad", isSinPublicidad);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onResume() {
		comprobarMenu();
		super.onResume();
	}

	public void comprobarMenu() {
		prefs = getActivity().getSharedPreferences("ficheroConf",
				Context.MODE_PRIVATE);
		seguridadAct = prefs.getBoolean("seguridadActivada", false);

		if (seguridadAct) {
			layoutActivar.setVisibility(View.GONE);
			layoutDesactivar.setVisibility(View.VISIBLE);
			layoutModificar.setVisibility(View.VISIBLE);
		} else {
			layoutActivar.setVisibility(View.VISIBLE);
			layoutDesactivar.setVisibility(View.GONE);
			layoutModificar.setVisibility(View.GONE);
		}
	}
}
