package com.suitejvg.suitesensores.sensores;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.suitejvg.suitesensores.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Vibracion extends Fragment {

    Button btnVibrar;
    Vibrator vibracion;
    final int VIBRATION_DURATION = 3000;
    ImageView imgSimpson;
    ScheduledExecutorService scheduler;
    public Vibracion() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibracion = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vibracion, container, false);

        btnVibrar = view.findViewById(R.id.btnVibrar);
        imgSimpson = view.findViewById(R.id.imgSimpson);
        scheduler = Executors.newSingleThreadScheduledExecutor();

        if (btnVibrar != null) {
            btnVibrar.setOnClickListener(v -> toogleVibracion(vibracion.hasVibrator()));
        }
        return view;
    }

    public void toogleVibracion(boolean tiene) {
        if (tiene) {
            vibracion.vibrate(VIBRATION_DURATION);
        }
        runAnimation();
    }

    private void runAnimation() {
        Glide.with(this).load(R.mipmap.simpon_vibracion_min).into(imgSimpson);
        setTimeOut();
    }

    private void stopAnimation() {
        Glide.with(this).clear(imgSimpson);
        imgSimpson.setImageResource(R.mipmap.simpon_vibracion_min);
    }

    private void setTimeOut() {
        scheduler.schedule(() -> {
            requireActivity().runOnUiThread(() -> {
                stopAnimation();
                Toast.makeText(requireContext(), "Vibración finalizada", Toast.LENGTH_SHORT).show();
            });
        }, VIBRATION_DURATION, TimeUnit.MILLISECONDS);
    }
}