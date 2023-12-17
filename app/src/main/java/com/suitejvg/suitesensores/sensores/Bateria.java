package com.suitejvg.suitesensores.sensores;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.suitejvg.suitesensores.R;

public class Bateria extends Fragment {
    private CircularProgressBar bateriaProgressBar;
    private TextView bateriaTV;

    public Bateria() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_bateria, container, false);

        bateriaProgressBar = fragmento.findViewById(R.id.bateriaProgressBar);
        bateriaTV = fragmento.findViewById(R.id.bateriaTV);
        requireActivity().registerReceiver(this.nivelBateria, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        return fragmento;
    }

    private final BroadcastReceiver nivelBateria = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            bateriaProgressBar.setProgressWithAnimation(level);
            bateriaTV.setText(level + "%");

            // Cambia el color del CircularProgressBar basado en el nivel de la batería
            int color = getColorForBatteryLevel(level);
            bateriaProgressBar.setProgressBarColor(color);
        }
    };

    private int getColorForBatteryLevel(int level) {
        // Lógica para determinar el color basado en el nivel de la batería
        // Puedes personalizar esto según tus preferencias
        if (level >= 70) {
            return getResources().getColor(R.color.green); // Por ejemplo, verde para niveles altos
        } else if (level >= 30) {
            return getResources().getColor(R.color.orange); // Por ejemplo, naranja para niveles medios
        } else {
            return getResources().getColor(R.color.red); // Por ejemplo, rojo para niveles bajos
        }
    }
}