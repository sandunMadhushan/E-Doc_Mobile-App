package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerpatientdetails extends AppCompatActivity {

    TextInputEditText p_name, p_address, p_age, p_nic, p_contactno;
    Button buttonRegister;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpatientdetails);

        p_name = findViewById(R.id.p_name);
        p_address = findViewById(R.id.p_address);
        p_age = findViewById(R.id.p_age);
        p_nic = findViewById(R.id.p_nic);
        p_contactno = findViewById(R.id.p_contactno);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");

                    String name = p_name.getText().toString();
                    String address = p_address.getText().toString();
                    String age = p_age.getText().toString();
                    String nic = p_nic.getText().toString();
                    String contactno = p_contactno.getText().toString();
//
//                    HelperClass helperClass = new HelperClass(name, address, age, nic, contactno);
//                    reference.child(nic).setValue(helperClass);

                    Toast.makeText(registerpatientdetails.this, "You have signup successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerpatientdetails.this, login.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(registerpatientdetails.this, "Error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*public void onButtonClicked(View view){
        Intent intent = new Intent(this, patient_home.class);
        startActivity(intent);
    }*/
}