package com.s22010304.e_doc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editEmail, editUsername, editPassword, editAddress, editAge, editContactNo;
    Button saveButton;
    String nameUser, emailUser, usernameUser, passwordUser, addressUser, ageUser, contactUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editAddress = findViewById(R.id.editAddress);
        editAge = findViewById(R.id.editAge);
        editContactNo = findViewById(R.id.editContactNo);
        saveButton = findViewById(R.id.saveButton);

        showData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isPasswordChanged() || isEmailChanged() || isAddressChanged() || isAgeChanged() || isContactChanged()) {
                    Toast.makeText(EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, ProfileDetails.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditProfileActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNameChanged() {
        if (!nameUser.equals(editName.getText().toString())) {
            reference.child(usernameUser).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        if (!emailUser.equals(editEmail.getText().toString())) {
            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }


    private boolean isPasswordChanged() {
        if (!passwordUser.equals(editPassword.getText().toString())) {
            reference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isAddressChanged() {
        if (!addressUser.equals(editAddress.getText().toString())) {
            reference.child(usernameUser).child("address").setValue(editAddress.getText().toString());
            addressUser = editAddress.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isAgeChanged() {
        if (!ageUser.equals(editAge.getText().toString())) {
            reference.child(usernameUser).child("age").setValue(editAge.getText().toString());
            ageUser = editAge.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isContactChanged() {
        if (!contactUser.equals(editContactNo.getText().toString())) {
            reference.child(usernameUser).child("age").setValue(editContactNo.getText().toString());
            contactUser = editContactNo.getText().toString();
            return true;
        } else {
            return false;
        }
    }


    public void showData() {

        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");
        passwordUser = intent.getStringExtra("password");
        addressUser = intent.getStringExtra("address");
        ageUser = intent.getStringExtra("age");
        contactUser = intent.getStringExtra("contact_no");

        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
        editPassword.setText(passwordUser);
        editAddress.setText(addressUser);
        editAge.setText(ageUser);
        editContactNo.setText(contactUser);
    }
}