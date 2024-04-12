package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GettingStarted2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started2);
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, GettingStarted3.class);
        startActivity(intent);
    }
}