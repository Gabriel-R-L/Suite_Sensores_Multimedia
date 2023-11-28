package com.suitejvg.suitesensores.sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

public class Brujula extends Fragment implements SensorEventListener {

    private ImageView imgCompass;
    TextView txtGrados;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private final float[] lastAccelerometer = new float[3];
    private final float[] lastMagnetometer = new float[3];
    private boolean lastAccelerometerSet = false;
    private boolean lastMagnetometerSet = false;
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationValues = new float[3];
    private static final float ALPHA = 0.3f; // Factor de suavizado
    private float smoothedAzimuth = 0;

    public Brujula() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brujula, container, false);

        imgCompass = view.findViewById(R.id.imgCompass);
        txtGrados = view.findViewById(R.id.txtGrados);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            // Si el magnetómetro está disponible, registra el SensorEventListener
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if (magnetometer != null && accelerometer != null) {
                // Registra el SensorEventListener para el magnetómetro y el acelerómetro
                sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            // Maneja la situación en la que el dispositivo no tiene un magnetómetro
            String TEXTO = "No se puede obtener la orientación del dispositivo";
            txtGrados.setText(TEXTO);
        }
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.d("SENSOR", "onSensorChanged: " + event.sensor.getType() + " | " + Sensor.TYPE_ACCELEROMETER + " " + Sensor.TYPE_MAGNETIC_FIELD);
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
                lastAccelerometerSet = true;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
                lastMagnetometerSet = true;
                break;
        }

        if (lastAccelerometerSet && lastMagnetometerSet) {
            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer);
            SensorManager.getOrientation(rotationMatrix, orientationValues);

            float azimuthInRadians = orientationValues[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);

            azimuthInDegrees = (azimuthInDegrees + 360) % 360;

            smoothedAzimuth = lowPass(azimuthInDegrees, smoothedAzimuth);
            updateUI(smoothedAzimuth);
        }
    }

    private float lowPass(float current, float last) {
        return last + ALPHA * (current - last);
    }

    private void updateUI(float azimuth) {
        // Rotate the compass image based on the azimuth angle
        imgCompass.setRotation(-azimuth);

        int rotation = (int) (azimuth);

        String text = rotation + "°";

        // Display the azimuth angle in the TextView
        txtGrados.setText(text);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPause() {
        super.onPause();
        // Detén la actualización del sensor cuando la actividad esté en pausa
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registra el SensorEventListener nuevamente al reanudar la actividad
        if (magnetometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Detén la actualización del sensor cuando la actividad esté en pausa
        sensorManager.unregisterListener(this);
    }
}