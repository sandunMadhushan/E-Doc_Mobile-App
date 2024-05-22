package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    TextInputEditText signupName, signupEmail, signupUsername, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    Spinner selectedOption;
    CheckBox TermsnConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        selectedOption = findViewById(R.id.spinnerOps);

        TermsnConditions = findViewById(R.id.termsandconditions);

        String[] option = {"Patient", "Doctor"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, option);
        selectedOption.setAdapter(adapter);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUsername() | !validatePassword() | !validateName() | !validateEmail()) {

                } else {

                    if (TermsnConditions.isChecked()){

                        database = FirebaseDatabase.getInstance();
                        reference = database.getReference("users");

                        String name = signupName.getText().toString();
                        String email = signupEmail.getText().toString();
                        String username = signupUsername.getText().toString();
                        String password = signupPassword.getText().toString();
                        String selectedOp = selectedOption.getSelectedItem().toString();

                        HelperClass helperClass = new HelperClass(name, email, username, password, selectedOp,null,null,null);
                        reference.child(username).setValue(helperClass);

                        Toast.makeText(signup.this, "You have signup successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(signup.this, login.class);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(signup.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });


    }

    public void onTextViewClicked(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, registeroption.class);
        startActivity(intent);
    }

    public Boolean validateUsername() {
        String val = signupUsername.getText().toString();
        String noWhiteSpace = "^[\\w]{4,20}$";

        if (val.isEmpty()) {
            signupUsername.setError("Username cannot be empty");
            return false;
        } else if (val.length() > 20) {
            signupUsername.setError("Username is too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            signupUsername.setError("Whitespace or special characters are not allowed");
            return false;
        } else {
            signupUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = signupPassword.getText().toString();
        String passwordVal = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        if (val.isEmpty()) {
            signupPassword.setError("Password cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            signupPassword.setError("Password must be at least 4 characters long, contain at least one letter, one special character, and have no spaces");
            return false;
        } else {
            signupPassword.setError(null);
            return true;
        }
    }

    public Boolean validateName() {
        String val = signupName.getText().toString();
        if (val.isEmpty()) {
            signupName.setError("Name cannot be empty");
            return false;
        } else if (val.length() > 20) {
            signupName.setError("Name is too long. Enter abc.comfirst name and last name");
            return false;
        }
        else {
            signupName.setError(null);
            return true;
        }
    }

    public Boolean validateEmail() {
        String val = signupEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            signupEmail.setError("Email cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            signupEmail.setError("Invalid email address");
            return false;
        } else {
            signupEmail.setError(null);
            return true;
        }
    }
}