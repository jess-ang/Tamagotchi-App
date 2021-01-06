package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfileActivity extends AppCompatActivity {
    private String name;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        SQLiteOpenHelper tamagotchiDatabaseHelper = new TamagotchiDatabaseHelper(this);

        try {
            SQLiteDatabase db = tamagotchiDatabaseHelper.getReadableDatabase();
            Cursor userCursor = db.query("USER",
                    new String[]{"_id", "NAME","EMAIL"},
                    null, null, null, null, null);

            if (userCursor.moveToFirst()) {
                name = userCursor.getString(1);
                email = userCursor.getString(2);
            }
            userCursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    @Override
    protected void onStart(){
        super.onStart();
        TextView nameText = (TextView) findViewById(R.id.name);
        nameText.setText("Name: "+ name);

        TextView emailText = (TextView) findViewById(R.id.email);
        emailText.setText("Email: "+email);

    }
    public void onClickPet(View view){
        Intent intent = new Intent(this,
                PetActivity.class);
        startActivity(intent);
    }
    public void onClickProfileChange(View view){
        EditText nameView = (EditText) findViewById(R.id.setName);
        EditText emailView = (EditText) findViewById(R.id.setEmail);
        String name = nameView.getText().toString();
        String email = emailView.getText().toString();
        ContentValues userValues = new ContentValues();
        userValues.put("NAME", name);
        userValues.put("EMAIL", email);
        SQLiteOpenHelper tamagotchiDatabaseHelper = new TamagotchiDatabaseHelper(this);

        try{
            SQLiteDatabase db = tamagotchiDatabaseHelper.getWritableDatabase();
            db.update("USER",userValues,"_id = ?",new String[] {"1"});
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        Intent intent = new Intent(this,
                MyProfileActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}