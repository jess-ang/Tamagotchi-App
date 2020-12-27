package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor petsCursor;
    private Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ListView listPets = (ListView) findViewById(R.id.list_pets);
        SQLiteOpenHelper tamagotchiDatabaseHelper = new TamagotchiDatabaseHelper(this);

        try{
            db = tamagotchiDatabaseHelper.getReadableDatabase();
            userCursor = db.query("USER",
                    new String[] {"_id","NAME","EMAIL","PET_SELECTED","PET_NAME"},
                    null, null, null, null, null);

            if (userCursor.moveToFirst()) {
                String nameText = userCursor.getString(1);
                String emailText = userCursor.getString(2);
                String petSelectedText = userCursor.getString(3);
                String petNameText = userCursor.getString(4);

                TextView nameView = (TextView) findViewById(R.id.name);
                TextView emailView = (TextView) findViewById(R.id.userEmail);
                TextView petSelectedView = (TextView) findViewById(R.id.petSelect);
                TextView petNameView = (TextView) findViewById(R.id.petName);
                nameView.setText(nameText);
                emailView.setText(emailText);
                petSelectedView.setText(petSelectedText);
                petNameView.setText(petNameText);
            }
            else {
                Intent intent = new Intent(this,WelcomeActivity.class);
                startActivity(intent);
            }

            petsCursor = db.query("PET",
                    new String[] {"_id","NAME"},
                    null, null, null, null, null);

            CursorAdapter petsAdapter =
                    new SimpleCursorAdapter(TestActivity.this,
                            android.R.layout.simple_list_item_1,
                            petsCursor,
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listPets.setAdapter(petsAdapter);

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        userCursor.close();
        petsCursor.close();
        db.close();
    }
}