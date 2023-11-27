package com.suitejvg.suitesensores.sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suitejvg.suitesensores.R;

import java.util.Objects;

public class Proximidad extends Fragment implements SensorEventListener {
    private TextView textView;
    private ImageView imageView;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Boolean isProximitySensorAvaiable;
    private Vibrator vibrator;
    public Proximidad() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) Objects.requireNonNull(requireActivity().getSystemService(Context.SENSOR_SERVICE));
        vibrator = (Vibrator) Objects.requireNonNull(requireActivity().getSystemService(Context.VIBRATOR_SERVICE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento = inflater.inflate(R.layout.fragment_sensor_proximity, container, false);

        //? Busco la id aquí porque en el onCreate peta. Aquí espero a que la view se cree y pueda encontrar el elemento
        textView = fragmento.findViewById(R.id.textView);
        imageView = fragmento.findViewById(R.id.imageView);


        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isProximitySensorAvaiable = true;
        } else {
            textView.setText("Sensor de proximidad no disponible");
            isProximitySensorAvaiable = false;
            imageView.setImageResource(R.drawable.crojo);
        }

        // Inflate the layout for this fragment
        return fragmento;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText(event.values[0] + " cm");
        if (event.values[0] == 0) {
            imageView.setImageResource(R.drawable.cverde);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        } else {
            imageView.setImageResource(R.drawable.crojo);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isProximitySensorAvaiable) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isProximitySensorAvaiable) {
            sensorManager.unregisterListener(this);
        }
    }
}