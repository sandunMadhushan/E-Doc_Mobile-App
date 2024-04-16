package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    /*public void onButtonClicked(View view) {
        Intent intent = new Intent(this, doctor_home.class);
        startActivity(intent);
    }*/
    public void onTextViewClicked(View view) {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }
}