package com.suitejvg.suitesensores.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.suitejvg.suitesensores.R;
public class TicTacPtp extends Fragment {
    public TicTacPtp() {
        // Required empty public constructor
    }
    public static TicTacPtp newInstance(String param1, String param2) {
        return new TicTacPtp();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tic_tac_ptp, container, false);

        WebView webView = view.findViewById(R.id.webView);

        String url = "file:///android_asset/tres_en_raya_ptp/index.html";

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