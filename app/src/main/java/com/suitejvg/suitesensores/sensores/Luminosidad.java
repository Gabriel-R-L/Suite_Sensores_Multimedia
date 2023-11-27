package com.suitejvg.suitesensores.sensores;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

import java.util.Objects;

public class Luminosidad extends Fragment implements SensorEventListener {
    private TextView textView;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private WindowManager.LayoutParams layoutParams;
    private ContentResolver cResolver;
    private Boolean isLightAvaiable;

    public Luminosidad() {

    }

    //! https://chat.openai.com/share/f32516bb-c15a-4e87-b0f3-f8ebe91c6d44

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) Objects.requireNonNull(requireActivity().getSystemService(Context.SENSOR_SERVICE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento = inflater.inflate(R.layout.fragment_luminosidad, container, false);

        textView = fragmento.findViewById(R.id.textView3);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            // Inicializar el ContentResolver
            cResolver = requireContext().getContentResolver();

            // Inicializar WindowManager.LayoutParams para ajustar el brillo
            layoutParams = requireActivity().getWindow().getAttributes();

            isLightAvaiable = true;
        } else {
            textView.setText(":(");
            Toast.makeText(requireActivity(), "Tu dispositivo no cuenta con un sensor de luz", Toast.LENGTH_SHORT).show();
            isLightAvaiable = false;
        }

        return fragmento;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registrar el listener del sensor de luz
        if (isLightAvaiable) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Desregistrar el listener del sensor de luz para ahorrar recursos
        if (isLightAvaiable) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Obtener el valor de la luminosidad del evento
        float luminosity = event.values[0];

        textView.setText("Funciona");

        // Ajustar el brillo de la pantalla en base a la luminosidad
        int brightness = (int) (luminosity / lightSensor.getMaximumRange() * 255);

        // Configurar el brillo en el sistema
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);

        // Configurar el brillo en la ventana actual
        layoutParams.screenBrightness = brightness / 255.0f;
        requireActivity().getWindow().setAttributes(layoutParams);
    }
}