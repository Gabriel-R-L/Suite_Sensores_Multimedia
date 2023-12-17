package com.suitejvg.suitesensores.sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.suitejvg.suitesensores.R;

import java.util.Objects;

public class Proximidad extends Fragment implements SensorEventListener {
    private ImageView imageView;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Boolean isProximitySensorAvaiable;
    private Vibrator vibrator;

    public Proximidad() {

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
        imageView = fragmento.findViewById(R.id.imageView);


        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isProximitySensorAvaiable = true;
        } else {
            isProximitySensorAvaiable = false;
        }

        // Inflate the layout for this fragment
        return fragmento;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) {
            fadeInGif();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        } else {
            fadeOutGif();
        }
    }

    private void fadeInGif() {
        Glide.with(this)
                .load(R.drawable.william_scared)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    private void fadeOutGif() {
        Glide.with(this)
                .clear(imageView);
        imageView.setImageResource(R.drawable.william_scared);
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