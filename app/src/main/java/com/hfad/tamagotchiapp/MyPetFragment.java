package com.hfad.tamagotchiapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MyPetFragment extends Fragment {
    private SQLiteDatabase db;
    private Cursor userCursor;
    private Cursor petCursor;
    private String petName;
    private int petImageId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteOpenHelper tamagotchiDatabaseHelper = new TamagotchiDatabaseHelper(getActivity());

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
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_pet, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        if(view != null){
            TextView petNameText = (TextView) view.findViewById(R.id.petNameTitle);
            petNameText.setText(petName);
            ImageView petImage = (ImageView) view.findViewById(R.id.petImage);
            petImage.setImageResource(petImageId);
        }
    }
}