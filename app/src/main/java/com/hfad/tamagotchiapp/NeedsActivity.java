package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class NeedsActivity extends AppCompatActivity {
    static final String[] messages = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};
    private String needMsg = "hola";
    private boolean newNeed;
    private boolean startRunNeed = true;
    private boolean wasRunning;
    private int counter = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needs);

        if (savedInstanceState != null) {
            newNeed = savedInstanceState.getBoolean("newNeed");
            needMsg = savedInstanceState.getString("need");
            counter = savedInstanceState.getInt("counter");
            startRunNeed = savedInstanceState.getBoolean("startRunNeed");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        if (!wasRunning) {
            runNeed();
            wasRunning = true;
        }
        displayMsg();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("newNeed", newNeed);
        savedInstanceState.putString("need", needMsg);
        savedInstanceState.putBoolean("startRunNeed",startRunNeed);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
        savedInstanceState.putInt("counter",counter);
        Log.v("save",String.valueOf(startRunNeed));

    }
    @Override
    protected void onPause() {
        super.onPause();
//        startRunNeed = false;
//        final TextView timeView = (TextView)findViewById(R.id.needsMsg);
//        timeView.setText(needMsg);
        Log.v("onPause",String.valueOf(startRunNeed));

    }
    @Override
    protected void onResume() {
        super.onResume();
//        startRunNeed = true;
        Log.v("resume",String.valueOf(startRunNeed));

    }

    public void onClickFeed(View view) {
        if(needMsg.equals(messages[0])) {
            needAttended();
        }
    }
    public void onClickPlay(View view) {
        if(needMsg.equals(messages[1])){
            needAttended();
        }
    }
    public void onClickBath(View view) {
        if(needMsg.equals(messages[2]) ){
            needAttended();
        }
    }
    public void onClickHeal(View view) {
        if(needMsg.equals(messages[3]) ) {
            needAttended();
        }
    }
    public void onClickSleep(View view) {
        if(needMsg.equals(messages[4])){
            needAttended();
        }
    }

    private void needAttended(){
        newNeed = true;
        needMsg = "Thanks!";
        startRunNeed = true;
    }

    public String getMsg(String[] mensajes){
        int index = new Random().nextInt(mensajes.length-1);
        return mensajes[index];
    }

    private void runNeed() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(startRunNeed){
                    if(!newNeed){
                        if(counter>4){
                            newNeed = true;
                        }
                        counter%=5;
                        counter++;
                    }
                    else {
                        needMsg = getMsg(messages);
                        counter = 0;
                    startRunNeed =false;
                    }
                    Log.v("start","run");
                }
                else
                {
                    Log.v("stop","run");

                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void displayMsg() {
        final TextView mensaje = (TextView)findViewById(R.id.needsMsg);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                mensaje.setText(needMsg);
//                Log.v("display", needMsg);
                handler.postDelayed(this, 100);
            }
        });
    }
}