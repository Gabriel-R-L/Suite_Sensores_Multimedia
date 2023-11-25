package com.suitejvg.suitesensores.sensores;

import android.content.Context;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suitejvg.suitesensores.R;

import java.util.Objects;

public class Brujula extends Fragment implements SensorEventListener {

    private ImageView imgCompass;
    TextView txtGrados;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetometer = new float[3];
    private boolean lastAccelerometerSet = false;
    private boolean lastMagnetometerSet = false;

    private float[] rotationMatrix = new float[9];
    private float[] orientationValues = new float[3];

    public Brujula() {
        // Required empty public constructor
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
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorManager != null) {
            // Si el magnetómetro está disponible, registra el SensorEventListener
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        } else {
            // Maneja la situación en la que el dispositivo no tiene un magnetómetro
            txtGrados.setText("Este dispositivo no tiene un magnetómetro.");
        }
        return view;
    }

//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        // Obtiene los valores de los tres ejes del magnetómetro
//        float[] magneticValues = event.values;
//
//        // Calcula el ángulo de rotación
//        float rotation = (float) Math.toDegrees(Math.atan2(magneticValues[1], magneticValues[0]));
//
////      ! que sea positivo
//        rotation = (rotation + 360) % 360;
//
//        String text = rotation + "°";
//
//        // Muestra el ángulo de rotación en el TextView
//        txtGrados.setText(text);
//
//        // Rota la aguja de la brújula
//        imgCompass.setRotation(-rotation);
//    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
            lastAccelerometerSet = true;
        } else if (event.sensor == magnetometer) {
            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
            lastMagnetometerSet = true;
        }

        if (lastAccelerometerSet && lastMagnetometerSet) {
            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer);
            SensorManager.getOrientation(rotationMatrix, orientationValues);

            float azimuthInRadians = orientationValues[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);

            azimuthInDegrees = (azimuthInDegrees + 360) % 360;

            updateUI(azimuthInDegrees);
        }
    }

    private void updateUI(float azimuth) {
        // Rotate the compass image based on the azimuth angle
//        Matrix matrix = new Matrix();
//        imgCompass.setScaleType(ImageView.ScaleType.MATRIX);
//        matrix.postRotate(azimuth, imgCompass.getWidth() / 2, imgCompass.getHeight() / 2);
//        imgCompass.setImageMatrix(matrix);
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