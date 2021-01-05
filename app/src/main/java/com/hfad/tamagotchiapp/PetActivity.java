package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PetActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor userCursor;
    private Cursor petCursor;
    private String petName;
    private int petImageId;

    static final String[] mensajes = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};
    private String estado = "hola";
    private boolean isRunning;
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
        setContentView(R.layout.activity_pet);

        SQLiteOpenHelper tamagotchiDatabaseHelper = new TamagotchiDatabaseHelper(this);

        try {
            db = tamagotchiDatabaseHelper.getReadableDatabase();
            userCursor = db.query("USER",
                    new String[]{"_id", "PET_SELECTED","PET_NAME"},
                    null, null, null, null, null);

            String pet="";
            if (userCursor.moveToFirst()) {
                pet = userCursor.getString(1);
                petName = userCursor.getString(2);
            }

            petCursor = db.query("PET",
                    new String[]{"_id", "IMAGE_RESOURCE_ID"},
                    "NAME = ?",
                    new String[] {pet}, null, null, null);

            if (petCursor.moveToFirst()) {
                petImageId = petCursor.getInt(1);
            }

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (savedInstanceState != null) {
            estado = savedInstanceState.getString("estado");
            isRunning = savedInstanceState.getBoolean("isRunning");
        }
        displayMsg();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("estado", estado);
        savedInstanceState.putBoolean("isRunning", isRunning);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, NeedService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        TextView petNameText = (TextView) findViewById(R.id.petNameTitle);
        petNameText.setText("My pet: "+petName);
        ImageView petImage = (ImageView) findViewById(R.id.petImage);
        petImage.setImageResource(petImageId);

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
            need.needMsg = "Thank you!";
        }
    }
    public void onClickPlay(View view) {
        if(estado.equals(mensajes[1])){
            need.wasAttended = true;
            need.needMsg = "I'm happy now!";

        }
    }
    public void onClickBath(View view) {
        if(estado.equals(mensajes[2]) ){
            need.wasAttended = true;
            need.needMsg = "I feel clean!";
        }
    }
    public void onClickHeal(View view) {
        if(estado.equals(mensajes[3]) ) {
            need.wasAttended = true;
            need.needMsg = "I feel better!";

        }
    }
    public void onClickSleep(View view) {
        if(estado.equals(mensajes[4])){
            need.wasAttended = true;
            need.needMsg = "Hi!";
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
                handler.postDelayed(this, 500);
            }
        });
    }
}