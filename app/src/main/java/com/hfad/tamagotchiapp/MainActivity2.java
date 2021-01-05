package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity2 extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor userCursor;

    static final String[] mensajes = {"I'm hungry","I'm bored","I got muddy","I feel sick","I'm sleepy"};

    private String estado = "hola";
    private StatsFragment frag;
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
        setContentView(R.layout.activity_main2);

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
        if (savedInstanceState != null) {
            estado = savedInstanceState.getString("estado");
        }


        //Attach the SectionsPagerAdapter to the ViewPager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        //Attach the ViewPager to the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);



        frag = (StatsFragment)
                getSupportFragmentManager().findFragmentById(R.id.stats_frag);
        displayMsg();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("estado", estado);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, NeedService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
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
    private void displayMsg() {

//        final TextView mensaje = (TextView)findViewById(R.id.needsMsg);
//        mensaje.setText("adios");
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                frag.setTextNeed("hola");
//                if (bound && need != null) {
//                    estado = need.needMsg;
//                }
//                mensaje.setText(estado);
                handler.postDelayed(this, 1000);
            }
        });
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MyPetFragment();
                case 1:
                    return new StatsFragment();
                case 2:
                    return new MyProfileFragment();
            }
            return null;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.mypet_tab);
                case 1:
                    return getResources().getText(R.string.stats_tab);
                case 2:
                    return getResources().getText(R.string.profile_tab);
            }
            return null;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        userCursor.close();
        db.close();
    }
}
