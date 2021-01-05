package com.hfad.tamagotchiapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsFragment extends Fragment {

    static final String[] mensajes = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};
    private View layout;
    private String estado = "hola";

    private NeedService need;
    private boolean bound = false;
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder binder) {
//            NeedService.NeedBinder needBinder =
//                    (NeedService.NeedBinder) binder;
//            need = needBinder.getNeed();
//            bound = true;
//        }
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            bound = false;
//        }
//    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState != null) {
//            estado = savedInstanceState.getString("estado");
//        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout =  inflater.inflate(R.layout.fragment_stats, container, false);
//        displayMsg(layout);
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("estado", estado);
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        Intent intent = new Intent(getActivity(), NeedService.class);
//        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (bound) {
//            getActivity().unbindService(connection);
//            bound = false;
//        }
//    }

    public void setTextNeed(String msg){
        final TextView mensaje = (TextView) layout.findViewById(R.id.needsMsg);
        final Handler handler = new Handler();
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
    private void displayMsg(View view) {

        final TextView mensaje = (TextView) view.findViewById(R.id.needsMsg);
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