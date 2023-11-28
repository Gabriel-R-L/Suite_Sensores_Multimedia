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
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

public class Pasos extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView txtPasos;
    private int stepCount = 0;
    private float[] gravity = new float[3];
    private float[] linearAcceleration = new float[3];
    private static final float ALPHA = 0.8f;
    private static final int ACCELERATION_THRESHOLD = 7;
    private static final int STEP_DELAY_NS = 800000000; // 0.8 segundos
    private long lastStepTimeNs = 0;
    private final String TEXT = "No se puede acceder al acelerómetro del dispositivo";

    public Pasos() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_pasos, container, false);

        txtPasos = view.findViewById(R.id.txtPasos);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        } else {
            txtPasos.setText(TEXT);
        }

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Filtrar la gravedad para obtener la aceleración lineal
            gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * event.values[0];
            gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * event.values[1];
            gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * event.values[2];

            linearAcceleration[0] = event.values[0] - gravity[0];
            linearAcceleration[1] = event.values[1] - gravity[1];
            linearAcceleration[2] = event.values[2] - gravity[2];

            // Detectar pasos basándose en cambios en la aceleración lineal
            if (isStepDetected(linearAcceleration)) {
                stepCount++;
                String text = "Pasos: " + stepCount;
                txtPasos.setText(text);
            }
        }
    }

//    ? detección de pasos con acelerómetro simple
    private boolean isStepDetected(float[] linearAcceleration) {
        // Calcular la magnitud de la aceleración lineal
        float magnitude = (float) Math.sqrt(linearAcceleration[0] * linearAcceleration[0] + linearAcceleration[1] * linearAcceleration[1] + linearAcceleration[2] * linearAcceleration[2]);

        // Verificar si la magnitud supera un umbral
        if (magnitude > ACCELERATION_THRESHOLD) {
            long currentTimeNs = System.nanoTime();

            // Verificar si ha pasado suficiente tiempo desde el último paso
            if (currentTimeNs - lastStepTimeNs > STEP_DELAY_NS) {
                lastStepTimeNs = currentTimeNs;
                return true; // Se detecta un paso
            }
        }

        return false; // No se detecta un paso
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (accelerometer != null) {
            sensorManager.unregisterListener(this, accelerometer);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (accelerometer != null) {
            sensorManager.unregisterListener(this, accelerometer);
        }
    }
}