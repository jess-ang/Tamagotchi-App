package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PetActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor userCursor;
    private Cursor petCursor;
    private String petName;
    private int petImageId;
    private Button saveImage;
    private String email;

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

        try{
            db = tamagotchiDatabaseHelper.getReadableDatabase();
            userCursor = db.query("USER",
                    new String[] {"_id","PET_SELECTED"},
                    null, null, null, null, null);

            if (!userCursor.moveToFirst()) {
                Intent intent = new Intent(this,WelcomeActivity.class);
                startActivity(intent);
            }
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        try {
            db = tamagotchiDatabaseHelper.getReadableDatabase();
            userCursor = db.query("USER",
                    new String[]{"_id", "PET_SELECTED","PET_NAME","EMAIL"},
                    null, null, null, null, null);

            String pet="";
            if (userCursor.moveToFirst()) {
                pet = userCursor.getString(1);
                petName = userCursor.getString(2);
                email = userCursor.getString(3);
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

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        saveImage = (Button)findViewById(R.id.photo);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("btn","press");
                saveToGallery();
            }
        });
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
        petNameText.setText(petName+" says:");
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
    public void onClickProfile(View view){
        Intent intent = new Intent(this,
                MyProfileActivity.class);
        startActivity(intent);
    }
    private void saveToGallery(){
//        ImageView imageView = findViewById(R.id.imageView);
        Bitmap bitmap = getPetPhoto();
//        imageView.setImageBitmap(bitmap);
        saveBitMap(bitmap);
    }
    private void saveBitMap(Bitmap bitmap){
        FileOutputStream outputStream = null;
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/MyPics/");
        dir.mkdirs();
        File file = new File(dir,System.currentTimeMillis()+".png");
        try{
            outputStream = new FileOutputStream(file);
            Log.v("New file", "ok");
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("New file", "fail");
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        Toast toast = Toast.makeText(this,"Image saved!", Toast.LENGTH_SHORT);
        toast.show();
        try{
            outputStream.flush();
            Log.v("flush", "ok");
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.v("flush", "fail");
        }
        try{
            outputStream.close();
            Log.v("close", "ok");
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.v("close", "fail");
        }
    }
    public void sendMail(View view){
        String[] TO_EMAILS = {email};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,TO_EMAILS);
        intent.putExtra(Intent.EXTRA_SUBJECT,"TamagotchiApp Reminder");
        intent.putExtra(Intent.EXTRA_TEXT,"Hello. Your pet says: "+need.needMsg);
        startActivity(Intent.createChooser(intent,"Send reminder via"));
    }
    //create bitmap from view and returns it
    private Bitmap getPetPhoto() {
//        LinearLayout savingLayout = (LinearLayout)findViewById(R.id.idForSaving);
        ImageView savingLayout = (ImageView) findViewById(R.id.petImage);
        Bitmap bitmap = Bitmap.createBitmap(savingLayout.getWidth(), savingLayout.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = savingLayout.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        }   else{
            canvas.drawColor(Color.WHITE);
        }
        savingLayout.draw(canvas);
        return bitmap;
    }
}