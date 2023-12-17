package com.suitejvg.suitesensores.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

import java.util.Objects;

public class HomeFragmen extends Fragment {

    Button ptp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // * Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        WebView webView = view.findViewById(R.id.webViewHome);

        String url = "https://vix890.github.io/React_tic_tac_toe/";

        // Configura las opciones del WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // Habilita el almacenamiento en caché
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Carga el archivo HTML desde assets
        webView.loadUrl(url);

        return view;
    }
}
