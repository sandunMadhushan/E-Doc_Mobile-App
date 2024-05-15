package com.s22010304.e_doc;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.s22010304.e_doc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    private String userName;
    private String profilePictureUri;

    ActivityMainBinding binding;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;*/


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

        binding.navView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_appointments:
                    replaceFragment(new AppointmentsFragment());
                    break;
                case R.id.nav_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        /*navigationView.setNavigationItemSelectedListener(this);*/

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
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


    /*public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AppointmentsFragment()).commit();
                break;
            case R.id.nav_share:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }*/

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}

