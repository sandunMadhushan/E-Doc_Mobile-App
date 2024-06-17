package com.s22010304.e_doc;



import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


import java.util.concurrent.Executor;

import com.s22010304.e_doc.databinding.ActivityMainBinding;



//uncomment this implement part to enable nav drawer
public class MainActivity extends AppCompatActivity /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    String username;
    private String nameFromDB;
    private String userName;
    private String profilePictureUri;
    private String name;
    private String userSelectedOp;
    ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    DrawerLayout drawer_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Retrieve saved user information
        retrieveUserInfo();

        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra("userName")) {
                userName = i.getStringExtra("userName");
            }
            if (i.hasExtra("profilePictureUri")) {
                profilePictureUri = i.getStringExtra("profilePictureUri");
            }
            if (i.hasExtra("nameFromDB")) {
                nameFromDB = i.getStringExtra("nameFromDB");
            }
            if (i.hasExtra("userSelectedOp")) {
                userSelectedOp = i.getStringExtra("userSelectedOp");
            }

            Log.d("main", "onCreate: "+userSelectedOp);

            // Save user info to SharedPreferences
            saveUserInfoLocally(userName, profilePictureUri, nameFromDB, userSelectedOp);
        }

        Log.d("PUBLC1", "onCreate: "+ userSelectedOp);

       /* if (userName != null || profilePictureUri != null) {
            replaceFragment(HomeFragment.newInstance(userName, profilePictureUri, nameFromDB));
        } else {
            replaceFragment(new HomeFragment());
        }
*/


            // Check if user information is retrieved, if not, display placeholder
        if (userName != null || profilePictureUri != null) {
            // Replace the current fragment with HomeFragment and pass user information

            replaceFragment(HomeFragment.newInstance(userName, profilePictureUri, name,userSelectedOp));
        } else {
            // Display placeholder image and text
            replaceFragment(new HomeFragment());
        }

        /*binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/

        // Check if user information is passed from LoginActivity
        Intent intent = getIntent();

      /*  if (intent != null) {
            if (intent.hasExtra("userName")) {
                userName = intent.getStringExtra("userName");
            }
            if (intent.hasExtra("profilePictureUri")) {
                profilePictureUri = intent.getStringExtra("profilePictureUri");
            }
            if (intent.hasExtra("name")) {
                name = intent.getStringExtra("name");

            }
            if (intent.hasExtra("userSelectedOp")) {
                userSelectedOp = intent.getStringExtra("userSelectedOp");
                Log.d("intent0", "onCreate: " + userSelectedOp);

            }
        }*/

//         userSelectedOp = intent.getStringExtra("userSelectedOp");
        Log.d("intent1", "onCreate: "+ userSelectedOp);

        Log.d("PUBLC2", "onCreate: "+ userSelectedOp);

        String adminUsername = intent.getStringExtra("adminUsername");

        String retrievedUsername = UserManager.getInstance().getUsername();


        if ("Patient".equals(userSelectedOp)) {
            // Replace the current fragment with HomeFragment and pass user information
            replaceFragment(HomeFragment.newInstance(userName, profilePictureUri, name,userSelectedOp));
            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.home:


                        replaceFragment(HomeFragment.newInstance(userName, profilePictureUri, name,userSelectedOp));

                        break;
                    case R.id.appointments:
                        replaceFragment(AppointmentsFragment.newInstance(userName, profilePictureUri, name));
                        break;
                    case R.id.profile:
                        replaceFragment(ProfileFragment.newInstance(userName, profilePictureUri, name));
                        break;
                }
                return true;
            });

        } else if ("Doctor".equals(userSelectedOp)) {

            replaceFragment(DoctorHomeFragment.newInstance(userName, profilePictureUri, name,userSelectedOp));
            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(DoctorHomeFragment.newInstance(userName, profilePictureUri, name,userSelectedOp));
                        break;
                    case R.id.appointments:
                        replaceFragment(DoctorAppointmentFragment.newInstance(userName,profilePictureUri,name,userSelectedOp));
                        break;
                    case R.id.profile:
                        replaceFragment(ProfileFragment.newInstance(userName,profilePictureUri,name));
                        break;
                }
                return true;
            });
        } else if ("edoc_admin".equals(adminUsername) || "edoc_admin".equals(userSelectedOp)) {
            intent.putExtra("adminUsername",adminUsername);
            binding.bottomNavigationView.setVisibility(View.GONE);
            replaceFragment(new AdminHome());

        }

        // Set up bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                if ("Patient".equals(userSelectedOp)) {


                    replaceFragment(HomeFragment.newInstance(userName, profilePictureUri, name,userSelectedOp));

                }
                else if ("Doctor".equals(userSelectedOp)) {
                    replaceFragment(DoctorHomeFragment.newInstance(userName, profilePictureUri, name, userSelectedOp));
                }

                else replaceFragment(HomeFragment.newInstance(userName, profilePictureUri, name,userSelectedOp));

            }
            else if (itemId == R.id.appointments) {
                if ("Patient".equals(userSelectedOp)) {
                    replaceFragment(AppointmentsFragment.newInstance(userName, profilePictureUri, name));
                }
                else if ("Doctor".equals(userSelectedOp)) {
                    replaceFragment(DoctorAppointmentFragment.newInstance(userName, profilePictureUri, name, userSelectedOp));
                }
                else replaceFragment(AppointmentsFragment.newInstance(userName, profilePictureUri, name));
            }
            else if (itemId == R.id.profile) {
                if ("Patient".equals(userSelectedOp)) {
                    replaceFragment(ProfileFragment.newInstance(userName, profilePictureUri, name));
                }
                else if ("Doctor".equals(userSelectedOp)) {
                    replaceFragment(DoctorProfileFragment.newInstance(userName, profilePictureUri, name));
                }
                else replaceFragment(ProfileFragment.newInstance(userName, profilePictureUri, name));
            }
            else {
                return false;
            }

            return true;
        });


        //uncomment these two sections for enable nav drawer

        /*binding.navView.setNavigationItemSelectedListener(menuItem -> {
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
        });*/

        /*drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
*/

        //Fingerprint auth

        /*drawer_layout = findViewById(R.id.drawer_layout);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "Device doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "Fingerprint is not working", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getApplicationContext(), "No fingerprint assigned", Toast.LENGTH_SHORT).show();
                break;
        }
        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Fingerprint Authenticated", Toast.LENGTH_SHORT).show();
                drawer_layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("E-DOC")
                .setDescription("Use your fingerprint to login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);*/


    }

    private void replaceFragment(Fragment fragment) {
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
        nameFromDB = prefs.getString("nameFromDB",null);
        userSelectedOp = prefs.getString("userSelectedOp",null);
        Log.d("mainretrive", "retrieveUserInfo: "+ userSelectedOp);
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

    //uncomment these sections for enable nav drawer

   /* public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }*/


    /*public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onBackPressed() {
        //commented after fingerprint auth
        //navigateMainActivity();

        super.onBackPressed();
    }

    public void navigateMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent navigating back to ProfileFragment on back press
    }

    private void saveUserInfoLocally(String userName, String profilePictureUri, String nameFromDB, String userSelectedOp) {
        SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.putString("userName", userName);
        editor.putString("profilePictureUri", profilePictureUri);
        editor.putString("nameFromDB", nameFromDB);
        editor.putString("userSelectedOp",userSelectedOp);
        Log.d("Main Activity", "saveUserInfoLocally: " + userSelectedOp);
        editor.apply();
    }

}