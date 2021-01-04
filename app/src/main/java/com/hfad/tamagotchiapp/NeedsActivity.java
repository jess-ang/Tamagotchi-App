package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class NeedsActivity extends AppCompatActivity {
    final String[] mensajes = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};
    private String need = "hola";
    private boolean newNeed;
    private boolean wasAttended;
    private boolean wasRunning;

    private int needTime = 15000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needs);
        if (savedInstanceState != null) {
            newNeed = savedInstanceState.getBoolean("newNeed");
            need = savedInstanceState.getString("need");
            wasAttended = savedInstanceState.getBoolean("wasAttended");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        else{
            wasRunning = true;
        }
        if(wasRunning){
            final TextView timeView = (TextView)findViewById(R.id.needsMsg);
            timeView.setText(need);
        }

        check();
        runTimer();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("newNeed", newNeed);
        savedInstanceState.putString("need", need);
        savedInstanceState.putBoolean("wasAttended",wasAttended);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        final TextView timeView = (TextView)findViewById(R.id.needsMsg);
//        timeView.setText(need);
//    }
    @Override
    protected void onResume() {
        super.onResume();
        final TextView timeView = (TextView)findViewById(R.id.needsMsg);
        timeView.setText(need);
    }
    @Override
    protected void onStart(){
        super.onStart();
        final TextView timeView = (TextView)findViewById(R.id.needsMsg);
        timeView.setText(need);
    }

    public void onClickFeed(View view) {
        if(need.equals(mensajes[0])) {
            wasAttended = true;
            Log.v("MainActivity","atentido");
        }
    }
    public void onClickPlay(View view) {
        if(need.equals(mensajes[1])){
            wasAttended = true;
            Log.v("MainActivity","atentido");

        }
    }
    public void onClickBath(View view) {
        if(need.equals(mensajes[2]) ){
            wasAttended = true;
            Log.v("MainActivity","atentido");
        }
    }
    public void onClickHeal(View view) {
        if(need.equals(mensajes[3]) ) {
            wasAttended = true;
            Log.v("MainActivity","atentido");
        }
    }
    public void onClickSleep(View view) {
        if(need.equals(mensajes[4])){
            wasAttended = true;
            Log.v("MainActivity","atentido");
        }
    }

    public String getMsg(String[] mensajes){
        int index = new Random().nextInt(mensajes.length-1);
        return mensajes[index];
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.needsMsg);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!newNeed) {
                    need = getMsg(mensajes);
                    newNeed = true;
                    timeView.setText(need);
                }
                handler.postDelayed(this, needTime);
            }
        });
    }

    private void check() {
        final TextView timeView = (TextView)findViewById(R.id.needsMsg);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(wasAttended) {
                    need = "gracias!";
                    timeView.setText(need);
                    wasAttended = false;
                    newNeed = false;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}