package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TimeActivity extends AppCompatActivity {

     final String[] mensajes = {"hambre","sueño","limpiar","sanar","jugar"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
    }

    public int getIndex(String[] mensajes){
        return new Random().nextInt(mensajes.length-1);
    }

    public void onClickStart(View view) {
//        super.onStart();
        Timer t = new Timer();
//        final TextView text = (TextView) findViewById(R.id.mensaje);
//        final Button btn = (Button) findViewById(R.id.btn);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_LONG).show();

//                TextView text = (TextView) findViewById(R.id.mensaje);
//                int random = new Random().nextInt(mensajes.length-1);
//                text.setText("hola");
//                   text.setRotation((text.getRotation()+10));
            }
        };
        t.schedule(tt,0,1000);
    }

}