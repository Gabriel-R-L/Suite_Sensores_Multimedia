package com.suitejvg.suitesensores.sensores;


import android.animation.ObjectAnimator;
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

public class Gravedad extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gravitySensor;
    private TextView gravityXTextView, gravityYTextView, gravityZTextView;
    private ImageView ballImageView;

    private float ballX = 0.00f;
    private float ballY = 0.00f;

    public Gravedad() {
        // Constructor vacío requerido por Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gravedad, container, false);

        gravityXTextView = view.findViewById(R.id.gravityX);
        gravityYTextView = view.findViewById(R.id.gravityY);
        gravityZTextView = view.findViewById(R.id.gravityZ);
        ballImageView = view.findViewById(R.id.ballImageView);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        }

        if (gravitySensor == null) {
            // El dispositivo no tiene el sensor de gravedad
            // Puedes manejar esta situación según tus necesidades
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (gravitySensor != null) {
            sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (gravitySensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            float gravityX = event.values[0];
            float gravityY = event.values[1];
            float gravityZ = event.values[2];
            String gravityXText = "" + gravityX;
            String gravityYText = "" + gravityY;
            String gravityZText = "" + gravityZ;

            gravityXTextView.setText(gravityXText);
            gravityYTextView.setText(gravityYText);
            gravityZTextView.setText(gravityZText);

            // Mover la pelota según la gravedad
            moveBall(gravityX, gravityY, gravityZ);
        }
    }

    private void moveBall(float gravityX, float gravityY, float gravityZ) {
        // Ajusta la posición de la pelota según la gravedad
        ballX -= gravityX * 2;
        ballY += gravityY * 2;

        // Limita la posición de la pelota dentro de la pantalla
        float maxX = ballImageView.getWidth() - ballImageView.getWidth() / 2;
        float maxY = ballImageView.getHeight() - ballImageView.getHeight() / 2;

        if (ballX < 0) ballX = 0;
        if (ballX > maxX) ballX = maxX;
        if (ballY < 0) ballY = 0;
        if (ballY > maxY) ballY = maxY;

        // Mueve la pelota con animación para cada eje
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(ballImageView, "translationX", ballX);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(ballImageView, "translationY", ballY);
        ObjectAnimator objectAnimatorZ = ObjectAnimator.ofFloat(ballImageView, "rotation", gravityZ);

        objectAnimatorX.start();
        objectAnimatorY.start();
        objectAnimatorZ.start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se necesita implementación específica para este ejemplo
    }
}
