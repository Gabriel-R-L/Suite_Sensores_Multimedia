package com.suitejvg.suitesensores.sensores;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

import java.util.Objects;


public class Bateria extends Fragment {
    private TextView bateriaTV;

    public Bateria() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_bateria, container, false);

        bateriaTV = fragmento.findViewById(R.id.bateriaTV);
        getActivity().registerReceiver(this.nivelBateria, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        return fragmento;
    }

    private BroadcastReceiver nivelBateria = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            bateriaTV.setText(String.valueOf(level)+ "%");
        }
    };
}