package com.suitejvg.suitesensores.sensores;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suitejvg.suitesensores.R;

import java.util.concurrent.Executor;

public class HuellaDigital extends Fragment {
    private TextView planbTV;
    private TextView respuestaTV;
    private ImageView respuestaIV;

    public HuellaDigital() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmento = inflater.inflate(R.layout.fragment_huella_digital, container, false);

        respuestaTV = fragmento.findViewById(R.id.respuestaTV);
        respuestaIV = fragmento.findViewById(R.id.respuestaIV);
        planbTV = fragmento.findViewById(R.id.planbTV);

        autenticarHuella(fragmento);

        planbTV.setOnClickListener(v -> {
            mostrarFragmentoDesbloqueado();
        });

        return fragmento;
    }

    private void autenticarHuella(View fragmento) {
        BiometricManager biometricManager = BiometricManager.from(fragmento.getContext());
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                respuestaIV.setImageResource(R.drawable.crying_sensor);
                respuestaTV.setText("No tienes sensor de huellas digital");
                planbTV.setVisibility(View.VISIBLE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                respuestaTV.setText("Ha sucedido un error inesperado");
                respuestaIV.setImageResource(R.drawable.baseline_question_mark_24);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                respuestaTV.setText("No hay huellas guardadas para acceder");
                respuestaIV.setImageResource(R.drawable.whatever_sensor);
                break;
            case BiometricManager.BIOMETRIC_SUCCESS:
                //? hay lector de huellas y hay huellas almacenadas
                mostrarBiometricPrompt(fragmento);
                break;
        }
    }

    private void mostrarBiometricPrompt(View fragmento) {
        Executor executor = ContextCompat.getMainExecutor(fragmento.getContext());
        BiometricPrompt biometricPrompt = new BiometricPrompt(HuellaDigital.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getActivity(), "Acceso autorizado", Toast.LENGTH_SHORT).show();
                mostrarFragmentoDesbloqueado();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        //* mostrar la burbuja con la huella
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Acerca tu huella").setDescription("").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
    }

    //? La gracia es acceder al fragmento al usar el lecto de huellas, pero si no cuentas con uno, no vas a poder.
    //? Para evitar eso, la llamada a este método en cualquier caso te soluciona el problema
    private void mostrarFragmentoDesbloqueado() {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Bateria()).commit();
    }
}