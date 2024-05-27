package com.s22010304.e_doc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    TextInputEditText loginUsername, loginPassword;
    String username;
    Button loginButton;
    TextView signupRedirectText;

    FirebaseAuth auth;
    Spinner selectedOption;

    private static final int RC_SIGN_IN = 123; // Request code for sign-in
    private GoogleSignInClient mGoogleSignInClient;

    // Variables for tracking the button clicks
    private int clickCount = 0;
    private static final int MAX_CLICKS = 5;
    private static final int TIMEOUT = 2000;

    private Handler handler = new Handler();
    private Runnable resetCounterRunnable = new Runnable() {
        @Override
        public void run() {
            clickCount = 0;
        }
    };

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

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.login_button);

        selectedOption = findViewById(R.id.spinnerOps);

        String[] option = {"Patient", "Doctor"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, option);
        selectedOption.setAdapter(adapter);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;

                // Remove any pending reset counter runnables
                handler.removeCallbacks(resetCounterRunnable);

                if (clickCount == MAX_CLICKS) {
                    // Reset the counter
                    clickCount = 0;
                    // Redirect to Admin Login
                    Intent intent = new Intent(login.this, AdminLoginActivity.class);
                    startActivity(intent);
                } else {
                    // Post a delayed reset counter runnable
                    handler.postDelayed(resetCounterRunnable, TIMEOUT);

                    if (!validateUsername() | !validatePassword()) {
                        // Validation failed, do not proceed
                    } else {
                        checkUser();
                    }
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = loginUsername.getText().toString();
        UserManager.getInstance().setUsername(userUsername);
        String userPassword = loginPassword.getText().toString();
        String userSelectedOp = selectedOption.getSelectedItem().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    String selectedOpFromDB = snapshot.child(userUsername).child("selectedOp").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)) {
                        loginUsername.setError(null);

                        // Check if the user option is correct
                        if (selectedOpFromDB.equals(userSelectedOp)) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("userSelectedOp", userSelectedOp);
                            intent.putExtra("userName",userUsername);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(login.this, "Select 'Login As' correctly", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupSignIn() {
        // Initialize Google sign-in options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageButton googleSignInButton = findViewById(R.id.googleBtn);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

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
                // Can now authenticate with Firebase using account.getIdToken()
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
        String profilePictureUri = user.getPhotoUrl().toString();

        // Save user info to SharedPreferences
        saveUserInfoLocally(userName, profilePictureUri);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userName", userName);
        intent.putExtra("profilePictureUri", profilePictureUri);

        startActivity(intent);
        finish();
    }


    private void navigateToProfileFragment(FirebaseUser user) {
        String userName = user.getDisplayName();
        Uri profilePictureUri = user.getPhotoUrl();

        HomeFragment homeFragment = new HomeFragment();

        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri.toString());
        homeFragment.setArguments(args);

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
