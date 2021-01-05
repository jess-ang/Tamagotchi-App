package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TimeActivity extends AppCompatActivity {
    static final String[] messages = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};
    private String needMsg = "hola";
    private boolean newNeed;
    private boolean wasAttended;
    private boolean wasRunning;
    private int counter = 8;
    private String estado = "hola";
    //    private TextView mensaje;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        if (savedInstanceState != null) {

            newNeed = savedInstanceState.getBoolean("newNeed");
            needMsg = savedInstanceState.getString("need");
            wasAttended = savedInstanceState.getBoolean("wasAttended");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        if (!wasRunning) {
            runNeed();
            displayMsg();

//            check();
            wasRunning = true;
        }
//        TextView mensaje = (TextView)findViewById(R.id.needsMsg);
//        mensaje.setText(needMsg);

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("newNeed", newNeed);
        savedInstanceState.putString("need", needMsg);
        savedInstanceState.putBoolean("wasAttended",wasAttended);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }
    @Override
    protected void onPause() {
        super.onPause();
        final TextView timeView = (TextView)findViewById(R.id.needsMsg);
        timeView.setText(needMsg);
    }
    @Override
    protected void onResume() {
        super.onResume();
        TextView timeView = (TextView)findViewById(R.id.needsMsg);
        timeView.setText(needMsg);
    }
//    @Override
//    protected void onStart(){
//        super.onStart();
//        TextView timeView = (TextView)findViewById(R.id.needsMsg);
//        timeView.setText(needMsg);
//    }

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
        TextView mensaje = (TextView)findViewById(R.id.needsMsg);
        needMsg = "Thank you!";
        mensaje.setText(needMsg);
        newNeed = false;
        counter = 0;
        Log.v("Btn","atentido");
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
//                if(!newNeed){
//                    if(counter>4){
                needMsg = getMsg(messages);
//                        needMsg = messages[0];
                newNeed = true;
                Log.v("runNeed", needMsg);
//                    }

//                counter%=5;
//                counter++;
//                }
                handler.postDelayed(this, 6000);
            }
        });
    }

    private void displayMsg() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView mensaje = (TextView)findViewById(R.id.needsMsg);
                mensaje.setText(needMsg);
                Log.v("display", needMsg);

                handler.postDelayed(this, 1000);
            }
        });
    }


    private void check() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.v("check", needMsg);
                if(wasAttended) {
                    needMsg = "Thank you!";
                    final TextView mensaje = (TextView)findViewById(R.id.needsMsg);
                    mensaje.setText(needMsg);
                    wasAttended = false;
                    newNeed = false;
                    counter = 0;
                    Log.v("check", needMsg);
                }
                handler.postDelayed(this, 100);
            }
        });
    }
}