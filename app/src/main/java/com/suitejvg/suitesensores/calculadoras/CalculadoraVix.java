package com.suitejvg.suitesensores.calculadoras;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.suitejvg.suitesensores.R;
public class CalculadoraVix extends Fragment {
    public CalculadoraVix() {
        // Required empty public constructor
    }

    public static CalculadoraVix newInstance() {
        return new CalculadoraVix();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculadora_vix, container, false);

        WebView webView = view.findViewById(R.id.webView);

        String url = "file:///android_asset/calculadora_vix/index.html";

        // Configura las opciones del WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        // Habilita el almacenamiento en caché
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Carga el archivo HTML desde assets
        webView.loadUrl(url);

        return view;
    }
}