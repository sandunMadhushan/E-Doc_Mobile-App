package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//after AppCompatActivity - implements View.OnClickListener
public class registeroption extends AppCompatActivity  {

    Button btnPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeroption);

        btnPatient = findViewById(R.id.buttonPatient);
        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registeroption.this, registerpatientdetails.class);
                startActivity(intent);
            }
        });


    }

    /*@Override
    public void onClick(View v) {
        if (v.getId()==R.id.button3){

            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }
    }*/

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.button3) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.container, new HomeFragment());
//            fragmentTransaction.addToBackStack(null); // Add this line to add the transaction to the back stack
//            fragmentTransaction.commit();
//        }
//    }


}

    /*public void onPatientButtonClicked(View view) {
        Intent intent = new Intent(this, registerpatientdetails.class);
        startActivity(intent);
    }

    public void onDoctorButtonClicked(View view){
        Intent intent = new Intent(this,regsiterdoctordetails.class);
        startActivity(intent);
    }*/

