package com.suitejvg.suitesensores.calculadoras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;


public class CalculadoraJona extends Fragment {
    public CalculadoraJona() {
        // Required empty public constructor
    }

    public static CalculadoraJona newInstance() {
        return new CalculadoraJona();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculadora_jona, container, false);

        WebView webView = view.findViewById(R.id.webView);

       // String url = "file://android_asset/calculadora_vix/index.html";
       String url = "https://vix890.github.io/Calculadora_React_Jona/index.html";

        // Configura las opciones del WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient()); // Abrir en el mismo WebView en lugar de un navegador externo
        webView.setWebChromeClient(new WebChromeClient());
        // Habilita el almacenamiento en caché
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Carga el archivo HTML desde assets
        webView.loadUrl(url);

        return view;
    }

}