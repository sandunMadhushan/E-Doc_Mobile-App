package com.s22010304.e_doc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfileFragment extends Fragment {
    private DatabaseReference usersRef;
    private DatabaseReference doctorsDetailsRef;

    private TextView nameTextView, emailTextView, usernameTextView;
    private EditText addressEditText, nicEditText, slmcNoEditText, contactNoEditText;

    private String username; // Assuming you have a way to get the logged-in user's username

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

       username = UserManager.getInstance().getUsername();


        // Initialize views
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        addressEditText = view.findViewById(R.id.addressEditText);
        nicEditText = view.findViewById(R.id.nicEditText);
        slmcNoEditText = view.findViewById(R.id.slmcNoEditText);
        contactNoEditText = view.findViewById(R.id.contactNoEditText);

        usernameTextView.setText(username);

        // Initialize Firebase references
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        doctorsDetailsRef = FirebaseDatabase.getInstance().getReference("doctors_details");

        // Fetch user data
        fetchUserData();



        // Handle form submission
        view.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDoctorDetails();
            }
        });

        return view;
    }

    private void fetchUserData() {
        usersRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                if (userModel != null) {
                    nameTextView.setText(userModel.name);
                    emailTextView.setText(userModel.email);
                    usernameTextView.setText(userModel.username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error while reading users data", databaseError.toException());
            }
        });
    }

    private void submitDoctorDetails() {
        String name = nameTextView.getText().toString();
        String email = emailTextView.getText().toString();
        String username = usernameTextView.getText().toString();
        String address = addressEditText.getText().toString();
        String nic = nicEditText.getText().toString();
        String slmcNo = slmcNoEditText.getText().toString();
        String contactNo = contactNoEditText.getText().toString();

        DoctorDetailsModel doctorDetailsModel = new DoctorDetailsModel(name, email, username, address, nic, slmcNo, contactNo);
        doctorsDetailsRef.child(username).setValue(doctorDetailsModel);
    }
}
