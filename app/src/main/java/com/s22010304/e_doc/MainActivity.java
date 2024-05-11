package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.s22010304.e_doc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String userName;
    private String profilePictureUri;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve saved user information
        retrieveUserInfo();

        // Check if user information is retrieved, if not, display placeholder
        if (userName != null && profilePictureUri != null) {
            // Replace the current fragment with HomeFragment and pass user information
            replaceFragment(HomeFragment.newInstance(userName, profilePictureUri));
        } else {
            // Display placeholder image and text
            replaceFragment(new HomeFragment());
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check if user information is passed from LoginActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userName") && intent.hasExtra("profilePictureUri")) {
            userName = intent.getStringExtra("userName");
            profilePictureUri = intent.getStringExtra("profilePictureUri");
        }

        // Replace the current fragment with HomeFragment and pass user information
        replaceFragment(HomeFragment.newInstance(userName, profilePictureUri));

        // Set up bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(HomeFragment.newInstance(userName, profilePictureUri));
                    break;
                case R.id.appointments:
                    replaceFragment(new AppointmentsFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Retrieve user information from SharedPreferences
    private void retrieveUserInfo() {
        SharedPreferences prefs = getSharedPreferences("UserInfo", MODE_PRIVATE);
        userName = prefs.getString("userName", null);
        profilePictureUri = prefs.getString("profilePictureUri", null);
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, GettingStarted2.class);
        startActivity(intent);
    }

    public void navigateToSignIn() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent navigating back to ProfileFragment on back press
    }
}
