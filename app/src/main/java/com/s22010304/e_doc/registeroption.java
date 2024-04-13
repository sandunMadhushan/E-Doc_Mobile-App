package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registeroption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeroption);
    }

    public void onPatientButtonClicked(View view) {
        Intent intent = new Intent(this, registerpatientdetails.class);
        startActivity(intent);
    }

    public void onDoctorButtonClicked(View view){
        Intent intent = new Intent(this,regsiterdoctordetails.class);
        startActivity(intent);
    }

    }