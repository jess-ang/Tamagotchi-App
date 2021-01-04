package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class PruebaActivity extends AppCompatActivity {
    static final String[] mensajes = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};

    private String estado = "hola";

    private NeedService need;
    private boolean bound = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            NeedService.NeedBinder needBinder =
                    (NeedService.NeedBinder) binder;
            need = needBinder.getNeed();
            bound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        if (savedInstanceState != null) {
            estado = savedInstanceState.getString("estado");
        }
        displayMsg();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("estado", estado);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, NeedService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }
    public void onClickFeed(View view) {
        if(estado.equals(mensajes[0])) {
            need.wasAttended = true;
            Log.v("MainActivity","atentido");
        }
    }
    public void onClickPlay(View view) {
        if(estado.equals(mensajes[1])){
            need.wasAttended = true;
            Log.v("MainActivity","atentido");

        }
    }
    public void onClickBath(View view) {
        if(estado.equals(mensajes[2]) ){
            need.wasAttended = true;
            Log.v("MainActivity","atentido");
        }
    }
    public void onClickHeal(View view) {
        if(estado.equals(mensajes[3]) ) {
            need.wasAttended = true;
            Log.v("MainActivity","atentido");
        }
    }
    public void onClickSleep(View view) {
        if(estado.equals(mensajes[4])){
            need.wasAttended = true;
            Log.v("MainActivity","atentido");
        }
    }
    private void displayMsg() {
        final TextView mensaje = (TextView)findViewById(R.id.needsMsg);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bound && need != null) {
                        estado = need.needMsg;
                }
                mensaje.setText(estado);
                handler.postDelayed(this, 1000);
            }
        });
    }
}