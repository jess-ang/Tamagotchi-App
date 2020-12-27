package com.hfad.tamagotchiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void onClickChoosePet(View view){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.petSelect);
        int id = radioGroup.getCheckedRadioButtonId();

        // Obtener el nombre del animal seleccionado
        RadioButton radioButton = (RadioButton) findViewById(id);
        String petSelected = radioButton.getText().toString();

        //Obtener el nombre para la mascota
        EditText messageView = (EditText) findViewById(R.id.setPetName);
        String petName = messageView.getText().toString();

        Intent intent = new Intent(this,
                CreateProfile.class);

        intent.putExtra(CreateProfile.EXTRA_PETNAME, petName);
        intent.putExtra(CreateProfile.EXTRA_PET, petSelected);

        startActivity(intent);
    }
}
