package com.agudoApp.salaryApp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.agudoApp.salaryApp.R;
import com.agudoApp.salaryApp.activities.DeleteRecibo;
import com.agudoApp.salaryApp.activities.EditRecibo;
import com.agudoApp.salaryApp.database.GestionBBDD;
import com.agudoApp.salaryApp.general.Recibos;

public class RecibosFragment extends Fragment {
	private static final String KEY_CONTENT = "RecibosFragment:Content";
	final GestionBBDD gestion = new GestionBBDD();
	static final int MENSAJE_OK_DELETE = 5;
	static final int MENSAJE_ERROR_DELETE = 4;
	static final int EDIT_RECIBO = 2;
	static final int DELETE_RECIBO = 3;

	private Button botonAddRecibo;
	private Button botonEditRecibo;
	private Button botonDeleteRecibo;

	SharedPreferences prefs;
	SharedPreferences.Editor editor;

	// Productos que posee el usuario
	boolean isPremium = false;
	boolean isCategoriaPremium = false;
	boolean isSinPublicidad = false;

	public RecibosFragment() {
	}

	public RecibosFragment(boolean isUserPremium, boolean isUserSinpublicidad,
			boolean isUserCategoriaPremium) {
		isPremium = isUserPremium;
		isCategoriaPremium = isUserCategoriaPremium;
		isSinPublicidad = isUserSinpublicidad;
	}

	private String mContent = "???";

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

		return inflater.inflate(R.layout.menu_recibos, container, false);
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

		botonAddRecibo = (Button) getView().findViewById(R.id.botonAddRecibo);
		botonEditRecibo = (Button) getView().findViewById(R.id.botonEditRecibo);
		botonDeleteRecibo = (Button) getView().findViewById(
				R.id.botonDeleteRecibo);


		botonAddRecibo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getActivity(), Recibos.class);
				startActivity(in);
			}
		});

		botonEditRecibo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getActivity(), EditRecibo.class);
				startActivity(in);
			}
		});

		botonDeleteRecibo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getActivity(), DeleteRecibo.class);
				startActivity(in);
			}
		});
	}

	// Aadiendo las opciones de men
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_setting, menu);
	}


}
