package com.hfad.tamagotchiapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;


public class NeedsService extends IntentService {
    public static final String[] mensajes = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};
    private String need = "hola";
    private boolean newNeed;
    private boolean wasAttended;
    private boolean wasRunning;
    private int needTime = 15000;
    public static final String EXTRA_MESSAGE = "message";
    public NeedsService() {
        super("DelayedMessageService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
        runTimer();
    }
    private void showText(final String text) {
        Log.v("Msg", "The message is: " + text);
    }
    public String getMsg(String[] mensajes){
        int index = new Random().nextInt(mensajes.length-1);
        return mensajes[index];
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
//                if (!newNeed) {
                    need = getMsg(mensajes);
                Log.v("Need", need);
//                    newNeed = true;
//                }
                handler.postDelayed(this, needTime);
            }
        });
    }
//
//    private void check() {
//        final Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if(wasAttended) {
//                    need = "gracias!";
//                    wasAttended = false;
//                    newNeed = false;
//                }
//                handler.postDelayed(this, 1000);
//            }
//        });
//    }
}
