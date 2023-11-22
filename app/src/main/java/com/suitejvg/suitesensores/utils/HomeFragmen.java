package com.suitejvg.suitesensores.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.suitejvg.suitesensores.R;

public class HomeFragmen extends Fragment {

    Button ptp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // * Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ptp = view.findViewById(R.id.ptp);
        ptp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "asd", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
