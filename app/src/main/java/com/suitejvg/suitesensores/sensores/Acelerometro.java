package com.suitejvg.suitesensores.sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

public class Acelerometro extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private TextView xTextView, yTextView, zTextView;
    private ImageView imageView;

    private float accelerationX, accelerationY, accelerationZ;

    public Acelerometro() {
        // Constructor vacío requerido por Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acelerometro, container, false);

        xTextView = view.findViewById(R.id.xTextView);
        yTextView = view.findViewById(R.id.yTextView);
        zTextView = view.findViewById(R.id.zTextView);
        imageView = view.findViewById(R.id.imageView);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        if (accelerometerSensor == null) {
            // El dispositivo no tiene el sensor de acelerómetro
            // Puedes manejar esta situación según tus necesidades
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (accelerometerSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerationX = event.values[0];
            accelerationY = event.values[1];
            accelerationZ = event.values[2];

            String xText = "Aceleración en X: " + accelerationX;
            String yText = "Aceleración en Y: " + accelerationY;
            String zText = "Aceleración en Z: " + accelerationZ;

            xTextView.setText(xText);
            yTextView.setText(yText);
            zTextView.setText(zText);

            // Cambiar la imagen cuando la aceleración en Z supere el umbral
            handleAccelerationChange();
        }
    }

    private void handleAccelerationChange() {
        float threshold = 9.8f; // Umbral de aceleración en Z

        if (accelerationZ > threshold) {
            imageView.setImageResource(R.drawable.totobobo);
        } else {
            imageView.setImageResource(R.drawable.totook);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se necesita implementación específica para este ejemplo
    }
}
