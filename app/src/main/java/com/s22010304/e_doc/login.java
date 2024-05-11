package com.s22010304.e_doc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {

    FirebaseAuth auth;

    private static final int RC_SIGN_IN = 123; // Request code for sign-in
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance();

        // Check if the user is already signed in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, navigate to ProfileFragment
            navigateToMainActivity(currentUser);
            return; // Skip the rest of onCreate method
        }

        // If user is not signed in, continue with sign-in setup
        setupSignIn();

    }

    private void setupSignIn() {
        // Initialize Google sign-in options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Use the web client ID from google-services.json
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Find ImageButton in the layout
        ImageButton googleSignInButton = findViewById(R.id.googleBtn);

        // Set an OnClickListener for the Google sign-in button
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the user clicks the sign-in button:
                // Sign out any existing user before proceeding with sign-in
                FirebaseAuth.getInstance().signOut();

                // Request the user to choose an account for sign-in
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Start sign-in intent after sign-out success
                            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                            startActivityForResult(signInIntent, RC_SIGN_IN);
                        } else {
                            Toast.makeText(login.this, "Failed to sign out", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // Handle the result of the sign-in process
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // You can now authenticate with Firebase using account.getIdToken()
                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Handle successful sign-in
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                navigateToMainActivity(user);
                            }
                        } else {
                            // Handle sign-in failure
                            Toast.makeText(login.this, "Failed to sign in: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (ApiException e) {
                // Handle sign-in failure
                Toast.makeText(login.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToMainActivity(FirebaseUser user) {
        String userName = user.getDisplayName();
        String profilePictureUri = user.getPhotoUrl().toString(); // Convert Uri to String

        // Save user info to SharedPreferences
        saveUserInfoLocally(userName, profilePictureUri);

        // Create a new instance of the HomeFragment
        HomeFragment homeFragment = new HomeFragment();

        // Pass necessary data to the fragment using arguments
        Bundle args = new Bundle();
        args.putString("userName", userName);
        if (profilePictureUri != null) {
            args.putString("profilePictureUri", profilePictureUri.toString());
        }
        homeFragment.setArguments(args);

        // Replace the current fragment with the HomeFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();

        // Navigate to MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("userName", userName); // Pass user name as extra
        intent.putExtra("profilePictureUri", profilePictureUri); // Pass profile picture URI as extra

        // Pass user information as extras
        /*intent.putExtra("userName", user.getDisplayName());
        if (user.getPhotoUrl() != null) {
            intent.putExtra("profilePictureUri", user.getPhotoUrl().toString());
        }*/
        startActivity(intent);
        finish(); // Close the current activity
    }


    private void navigateToProfileFragment(FirebaseUser user) {
        String userName = user.getDisplayName();
        Uri profilePictureUri = user.getPhotoUrl();

        // Create a new instance of the ProfileFragment
        HomeFragment homeFragment = new HomeFragment();

        // Pass any necessary data to the fragment using arguments
        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri.toString());
        homeFragment.setArguments(args);

        // Replace the current fragment with the ProfileFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment) // R.id.fragment_container is the ID of your fragment container view
                .commit();
    }

    public void navigateToSignIn() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent navigating back to HomeFragment on back press
    }

    private void saveUserInfoLocally(String userName, String profilePictureUri) {
        SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.putString("userName", userName);
        editor.putString("profilePictureUri", profilePictureUri);
        editor.apply();
    }


    public void onTextViewClicked(View view) {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }
}