package com.s22010304.e_doc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DoctorProfileFragment extends Fragment {
    private DatabaseReference usersRef;
    private DatabaseReference doctorsDetailsRef;

    private TextView nameTextView, emailTextView, usernameTextView;
    private EditText addressEditText, nicEditText, slmcNoEditText, contactNoEditText, specialAreaEditText, workAddressEditText, homeAddressEditText;

    private String username;

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
        specialAreaEditText = view.findViewById(R.id.specialAreaEditText);
        workAddressEditText = view.findViewById(R.id.workAddressEditText);
        homeAddressEditText = view.findViewById(R.id.homeAddressEditText);

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
                if (validateFields()) {
                    submitDoctorDetails();
                    Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                    sendNotificationToAdmin();
                }
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
        String specialArea = specialAreaEditText.getText().toString();
        String workAddress = workAddressEditText.getText().toString();
        String homeAddress = homeAddressEditText.getText().toString();


        DoctorDetailsModel doctorDetailsModel = new DoctorDetailsModel(name, email, username, address, nic, slmcNo, contactNo, specialArea, workAddress, homeAddress);
        doctorsDetailsRef.child(username).setValue(doctorDetailsModel);
    }

    private void sendNotificationToAdmin() {
        DatabaseReference adminTokenRef = FirebaseDatabase.getInstance().getReference("admin_fcm_tokens").child("edoc_admin");
        adminTokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String adminFCMToken = dataSnapshot.getValue(String.class);
                    if (adminFCMToken != null) {
                        sendNotification(adminFCMToken);
                    } else {
                        Log.e("Admin Token", "Admin FCM token is null");
                    }
                } else {
                    Log.e("Admin Token", "Admin FCM token not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error reading admin FCM token", databaseError.toException());
            }
        });
    }

    private void sendNotification(String adminFCMToken) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder(adminFCMToken + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(new Random().nextInt(9999)))
                .addData("title", "New Doctor Approval Pending")
                .addData("body", "You have new doctor approval pending")
                .build());
    }

    private boolean validateFields() {
        if (addressEditText.getText().toString().isEmpty()) {
            addressEditText.setError("Address is required");
            addressEditText.requestFocus();
            return false;
        }
        if (nicEditText.getText().toString().isEmpty()) {
            nicEditText.setError("NIC is required");
            nicEditText.requestFocus();
            return false;
        }
        if (slmcNoEditText.getText().toString().isEmpty()) {
            slmcNoEditText.setError("SLMC No is required");
            slmcNoEditText.requestFocus();
            return false;
        }
        if (contactNoEditText.getText().toString().isEmpty()) {
            contactNoEditText.setError("Contact No is required");
            contactNoEditText.requestFocus();
            return false;
        }
        if (specialAreaEditText.getText().toString().isEmpty()) {
            specialAreaEditText.setError("Special Area is required");
            specialAreaEditText.requestFocus();
            return false;
        }
        if (workAddressEditText.getText().toString().isEmpty()) {
            workAddressEditText.setError("Work Address is required");
            workAddressEditText.requestFocus();
            return false;
        }
        if (homeAddressEditText.getText().toString().isEmpty()) {
            homeAddressEditText.setError("Home Address is required");
            homeAddressEditText.requestFocus();
            return false;
        }
        return true;
    }




}
