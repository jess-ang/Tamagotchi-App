package com.hfad.tamagotchiapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfileFragment extends Fragment {
    private SQLiteDatabase db;
    private Cursor userCursor;
    private String name;
    private String email;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteOpenHelper tamagotchiDatabaseHelper = new TamagotchiDatabaseHelper(getActivity());

        try {
            db = tamagotchiDatabaseHelper.getReadableDatabase();
            userCursor = db.query("USER",
                    new String[]{"_id", "NAME","EMAIL"},
                    null, null, null, null, null);

            if (userCursor.moveToFirst()) {
                name = userCursor.getString(1);
                email = userCursor.getString(2);
            }

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        if(view != null){
            TextView nameText = (TextView) view.findViewById(R.id.name);
            nameText.setText(name);

            TextView emailText = (TextView) view.findViewById(R.id.email);
            emailText.setText(email);
        }
    }
}