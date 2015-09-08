package com.agudoApp.salaryApp.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.agudoApp.salaryApp.R;
import com.agudoApp.salaryApp.adapters.ListAdapterCategorias;
import com.agudoApp.salaryApp.database.GestionBBDD;
import com.agudoApp.salaryApp.model.Categoria;

import java.util.ArrayList;

public class PestanaSubcategoriaFragment extends Fragment {
	private static final String KEY_CONTENT = "PestanaCategoriaFragment:Content";
	private final String BD_NOMBRE = "BDGestionGastos";
	final GestionBBDD gestion = new GestionBBDD();
	private SQLiteDatabase db;
	protected ListView listCatView;
	private boolean isPremium = false;

	private String mContent = "???";
	
	public PestanaSubcategoriaFragment(boolean isUserPremium) {
		isPremium = isUserPremium;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.pestana_categoria, container, false);
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

		listCatView = (ListView) this.getView().findViewById(
				R.id.listaPestanaCategoria);

		// Bundle bundle = getArguments();
		// isPremium = bundle.getBoolean("isPremium", false);

		rellenarListaExpansibleCategorias();
	}

	public void rellenarListaExpansibleCategorias() {

		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		ArrayList<Object> opciones = new ArrayList<Object>();
		opciones.add("1");

		categorias = obtenerSubcategorias();
		listCatView.setClickable(true);

		boolean isCatPremium = false;
		if(isPremium || isCatPremium){
			isCatPremium = true;
		}
		ListAdapterCategorias listAdapter = new ListAdapterCategorias(
				getActivity(), categorias);
		listCatView.setAdapter(listAdapter);
	}

	public ArrayList<Categoria> obtenerSubcategorias() {
		ArrayList<Categoria> listSubcategorias = new ArrayList<Categoria>();
		db = getActivity().openOrCreateDatabase(BD_NOMBRE, 1, null);
		if (db != null) {
			// Recuperamos el listado del spinner Categorias
			listSubcategorias = (ArrayList<Categoria>) gestion
					.getCategoriasEditDelete(db, "Subcategorias",
							"idSubcategoria");
		}
		return listSubcategorias;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		rellenarListaExpansibleCategorias();
	}
}
