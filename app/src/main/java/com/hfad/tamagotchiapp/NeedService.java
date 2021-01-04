package com.hfad.tamagotchiapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

public class NeedService extends Service {
    static final String[] messages = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};
    public static String needMsg = "Hi";
    public static boolean wasAttended;
    public static boolean newNeed;
    private int counter = 8;

    private final IBinder binder = new NeedBinder();
    private final Random random = new Random();
    public class NeedBinder extends Binder {
        NeedService getNeed() {
            return NeedService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        runNeed();
        check();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
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
                if(!newNeed){
                    if(counter>4){
                        needMsg = getMsg(messages);
                        newNeed = true;
                        Log.v("Nuevo servicio", needMsg);
                    }
                }
                counter%=5;
                counter++;
                Log.v("Segundos", String.valueOf(counter));
                handler.postDelayed(this, 1000);
            }
        });
    }
    private void check() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(wasAttended) {
                    needMsg = "Thank you!";
                    wasAttended = false;
                    newNeed = false;
                    counter = 0;
                }
                handler.postDelayed(this, 100);
            }
        });
    }
}
