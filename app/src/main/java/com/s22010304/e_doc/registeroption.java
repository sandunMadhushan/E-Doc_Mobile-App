package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registeroption extends AppCompatActivity implements View.OnClickListener {

    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeroption);

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button3){

            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }
    }

    }

    /*public void onPatientButtonClicked(View view) {
        Intent intent = new Intent(this, registerpatientdetails.class);
        startActivity(intent);
    }

    public void onDoctorButtonClicked(View view){
        Intent intent = new Intent(this,regsiterdoctordetails.class);
        startActivity(intent);
    }*/

