package com.s22010304.e_doc;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class AdminHome extends Fragment {

    LinearLayout approvedoctor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        saveUserInfoLocally();


        MaterialButton logoutButton = view.findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });


        approvedoctor=view.findViewById(R.id.approvedoctor);

        approvedoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApproveDoctor.class);
                startActivity(intent);
            }
        });

        Intent intent = getActivity().getIntent();
        String adminUsername = intent.getStringExtra("adminUsername");

            /*// Get FCM token
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            String adminFCMToken = task.getResult();
                            // Store the FCM token in the database
                            assert adminUsername != null;
                            DatabaseReference adminTokenRef = FirebaseDatabase.getInstance()
                                    .getReference("admin_fcm_tokens")
                                    .child(adminUsername);
                            adminTokenRef.setValue(adminFCMToken)
                                    .addOnSuccessListener(aVoid -> Log.d("Admin Token", "Admin FCM token stored successfully"))
                                    .addOnFailureListener(e -> Log.e("Admin Token", "Error storing admin FCM token", e));
                        } else {
                            Log.e("Admin Token", "Unable to get FCM token for admin", task.getException());
                        }
                    });*/




        return view;
    }

    public static DoctorHomeFragment newInstance(String userName, String profilePictureUri, String name, String userSelectedOp) {
        DoctorHomeFragment fragment = new DoctorHomeFragment();
        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri);
        args.putString("name", name);
        args.putString("userSelectedOp", userSelectedOp);
        fragment.setArguments(args);
        return fragment;
    }

    private void saveUserInfoLocally() {
        String userSelectedOp = "edoc_admin";
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.putString("userSelectedOp", userSelectedOp);

        editor.apply();
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        clearUserInfoLocally();
        navigateToLoginActivity();
    }
    private void clearUserInfoLocally() {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.clear().apply();
    }
    private void navigateToLoginActivity() {
        Intent intent = new Intent(requireContext(), login.class);
        startActivity(intent);
        requireActivity().finish();
    }
}