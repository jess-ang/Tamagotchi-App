package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CreateProfile extends AppCompatActivity {
    private String petName;
    private String petSelected;

    public static final String EXTRA_PET = "pet";
    public static final String EXTRA_PETNAME = "petName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Intent intent = getIntent();
        petName = intent.getStringExtra(EXTRA_PETNAME);
        petSelected = intent.getStringExtra(EXTRA_PET);
    }

    public void onClickUser(View view){
        //Obtener el nombre del usuario
        EditText nameView = (EditText) findViewById(R.id.setName);
        EditText emailView = (EditText) findViewById(R.id.setEmail);
        CheckBox sendAlerts = (CheckBox) findViewById(R.id.sendAlerts);

        String name = nameView.getText().toString();
        String email = emailView.getText().toString();

        ContentValues userValues = new ContentValues();
        userValues.put("NAME", name);
        userValues.put("EMAIL", email);
        userValues.put("ALERT",sendAlerts.isChecked());
        userValues.put("PET_SELECTED", petSelected);
        userValues.put("PET_NAME", petName);

        SQLiteOpenHelper tamagotchiDatabaseHelper = new TamagotchiDatabaseHelper(this);

        try{
            SQLiteDatabase db = tamagotchiDatabaseHelper.getReadableDatabase();
            db.insert("USER",null,userValues);
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        Intent intent = new Intent(this,
                MainActivity.class);

        startActivity(intent);
    }
}