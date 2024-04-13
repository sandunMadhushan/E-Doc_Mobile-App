package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void onTextViewClicked(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, registeroption.class);
        startActivity(intent);
    }
}