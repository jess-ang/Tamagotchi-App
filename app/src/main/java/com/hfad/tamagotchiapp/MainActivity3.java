package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {
        Button saveImage;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main3);

            ActivityCompat.requestPermissions(MainActivity3.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            ActivityCompat.requestPermissions(MainActivity3.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            saveImage = (Button)findViewById(R.id.photo);
            saveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveToGallery();
                }
            });
        }
        public void onClickSave(View view){
            ImageView imageView = findViewById(R.id.imageView);
            Bitmap bitmap = getPetPhoto();
            imageView.setImageBitmap(bitmap);
            saveBitMap(bitmap);
        }

        private void saveToGallery(){
            ImageView imageView = findViewById(R.id.imageView);
            Bitmap bitmap = getPetPhoto();
            imageView.setImageBitmap(bitmap);
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

        //create bitmap from view and returns it
        private Bitmap getPetPhoto() {
            LinearLayout savingLayout = (LinearLayout)findViewById(R.id.idForSaving);
            Bitmap bitmap = Bitmap.createBitmap(savingLayout.getWidth(), savingLayout.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Drawable bgDrawable = savingLayout.getBackground();
            if (bgDrawable!=null) {
                bgDrawable.draw(canvas);
            }   else{
                canvas.drawColor(Color.BLUE);
            }
            savingLayout.draw(canvas);
            return bitmap;
        }
    }