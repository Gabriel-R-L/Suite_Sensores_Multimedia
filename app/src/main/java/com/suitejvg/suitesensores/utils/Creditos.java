package com.suitejvg.suitesensores.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

public class Creditos extends Fragment {

    private ImageView github;

    public Creditos() {
        // Required empty public constructor
    }


    public static Creditos newInstance() {
        Creditos fragment;
        fragment = new Creditos();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_creditos, container, false);

        github = view.findViewById(R.id.github);

        github.setOnClickListener(v -> {
            //* Mostrar el Toast
            Toast.makeText(getActivity(), "Viajando al GitHub del proyecto", Toast.LENGTH_SHORT).show();
//*             Crear un Intent con la acción de ver
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/Gabriel-R-L/Suite_Sensores_Multimedia/tree/main"));
            //* Iniciar la actividad
            startActivity(intent);
        });
        return view;
    }
}