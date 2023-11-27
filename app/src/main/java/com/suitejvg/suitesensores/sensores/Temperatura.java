package com.suitejvg.suitesensores.sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

import java.util.Objects;


public class Temperatura extends Fragment implements SensorEventListener {
    private TextView textView;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Boolean isTemplateSensorAvaiable;

    public Temperatura() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) Objects.requireNonNull(requireActivity().getSystemService(Context.SENSOR_SERVICE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_temperatura, container, false);

        textView = fragmento.findViewById(R.id.textView2);

        //? Comprobar que el dispositivo cuenta con este sensor
        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemplateSensorAvaiable = true;
        } else {
            textView.setText(":(");
            Toast.makeText(requireActivity(), "Tu dispositivo no cuenta con un sensor de temperatura", Toast.LENGTH_SHORT).show();
            isTemplateSensorAvaiable = false;
        }

        return fragmento;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText(event.values[0] + " ºC");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();

        if (isTemplateSensorAvaiable) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (isTemplateSensorAvaiable) {
            sensorManager.unregisterListener(this);
        }
    }
}