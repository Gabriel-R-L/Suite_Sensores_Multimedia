package com.suitejvg.suitesensores.sensores;import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

public class Rotacion extends Fragment {

    private ImageView totodileImg;
    private ImageView feraligatr1;

    public Rotacion() {
        // Constructor vacío requerido por Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rotacion, container, false);
        totodileImg = view.findViewById(R.id.totodileImg);
        feraligatr1 = view.findViewById(R.id.feraligatr1); // Inicializa feraligatr1

        // Lógica inicial para manejar la orientación al crear el fragmento
        handleRotation(getResources().getConfiguration());

        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Lógica para manejar la rotación cuando ocurre
        handleRotation(newConfig);
    }

    private void handleRotation(Configuration config) {
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Cambiar la imagen cuando está en orientación horizontal
            feraligatr1.setImageResource(R.drawable.fanarta);
            totodileImg.setVisibility(View.GONE);
            feraligatr1.setVisibility(View.VISIBLE);
        } else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Cambiar la imagen cuando está en orientación vertical
            totodileImg.setImageResource(R.drawable.totod2);
            feraligatr1.setVisibility(View.GONE);
            totodileImg.setVisibility(View.VISIBLE);
        }
    }
}
