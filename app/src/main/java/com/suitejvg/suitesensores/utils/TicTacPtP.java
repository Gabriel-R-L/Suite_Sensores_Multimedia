package com.suitejvg.suitesensores.utils;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.suitejvg.suitesensores.main.MainActivity;

public class TicTacPtP {

    private final MainActivity mActivity;
    private String jugador="x";

    public TicTacPtP(MainActivity activity) {
        this.mActivity = activity;
    }

    private void verificarWin(String turno) {
        //Referencia de los 9 botones

        String casilla1=mActivity.btn1.getText().toString();
        String casilla2=mActivity.btn2.getText().toString();
        String casilla3=mActivity.btn3.getText().toString();
        String casilla4=mActivity.btn4.getText().toString();
        String casilla5=mActivity.btn5.getText().toString();
        String casilla6=mActivity.btn6.getText().toString();
        String casilla7=mActivity.btn7.getText().toString();
        String casilla8=mActivity.btn8.getText().toString();
        String casilla9=mActivity.btn9.getText().toString();

        //Win Line One ---
        if(casilla1.equals(turno) && casilla2.equals(turno) && casilla3.equals(turno))
            win(turno);
        //Win Line Two ---
        if(casilla4.equals(turno) && casilla5.equals(turno) && casilla6.equals(turno))
            win(turno);
        //Win line Three ---
        if(casilla7.equals(turno) && casilla8.equals(turno) && casilla9.equals(turno))
            win(turno);
        //Win Line |
        if(casilla1.equals(turno) && casilla4.equals(turno) && casilla7.equals(turno))
            win(turno);
        //Win Line |
        if(casilla2.equals(turno) && casilla5.equals(turno) && casilla8.equals(turno))
            win(turno);
        //Win line |
        if(casilla3.equals(turno) && casilla6.equals(turno) && casilla9.equals(turno))
            win(turno);
        // Win \
        if(casilla1.equals(turno) && casilla5.equals(turno) && casilla9.equals(turno))
            win(turno);
        //Win /
        if(casilla3.equals(turno) && casilla5.equals(turno) && casilla6.equals(turno))
            win(turno);

    }

    private void win(String jugador) {
        String win;
        if(jugador.equals("x")){
            win="player1";
        }else{
            win="player2";
        }
        Toast.makeText(mActivity.getApplicationContext(), "Gano el Jugador: "+win,Toast.LENGTH_LONG).show();
        mActivity.btn1.setEnabled(false);
        mActivity.btn2.setEnabled(false);
        mActivity.btn3.setEnabled(false);
        mActivity.btn4.setEnabled(false);
        mActivity.btn5.setEnabled(false);
        mActivity.btn6.setEnabled(false);
        mActivity.btn7.setEnabled(false);
        mActivity.btn8.setEnabled(false);
        mActivity.btn9.setEnabled(false);
    }

    private void cambiarJugador() {
        if(jugador.equals("x")){
            jugador="o";
        }else{
            jugador="x";
        }
    }

    public void presion(View v){
        Button b=(Button)v;//v View
        //validacion x/o
        if(b.getText().toString().equals("")) {
            b.setText(jugador);
            verificarWin(jugador);
            cambiarJugador();
        } else {
            Toast.makeText(mActivity.getApplicationContext(), "Casilla ocupada",Toast.LENGTH_LONG).show();
        }
    }
}
