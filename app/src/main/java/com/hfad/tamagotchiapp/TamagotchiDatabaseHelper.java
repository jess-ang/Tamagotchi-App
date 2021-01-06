package com.hfad.tamagotchiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class TamagotchiDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "tamagotchi"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    TamagotchiDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }
    private static void insertPet(SQLiteDatabase db, String name, int resourceId){
        ContentValues petValues = new ContentValues();
        petValues.put("NAME", name);
        petValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("PET",null,petValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE PET (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "EMAIL TEXT, "
                    + "PET_SELECTED TEXT,"
                    + "PET_NAME TEXT);");

            insertPet(db,"Dog",R.drawable.dog);
            insertPet(db,"Cat",R.drawable.kitty);
            insertPet(db,"Bunny",R.drawable.bunny);

        }
    }
}
