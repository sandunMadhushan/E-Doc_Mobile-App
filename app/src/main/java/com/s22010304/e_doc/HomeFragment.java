package com.s22010304.e_doc;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    private String userName;
    private String profilePictureUri;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userName");
            profilePictureUri = getArguments().getString("profilePictureUri");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find views by ID
        TextView userNameTextView = view.findViewById(R.id.text_user_name);
        ImageView profileImageView = view.findViewById(R.id.profileImage);

        // Set user name
        userNameTextView.setText(userName);

        // Load profile picture using Glide library (assuming you have Glide configured)
        Glide.with(requireContext()).load(Uri.parse(profilePictureUri)).into(profileImageView);

        MaterialButton logoutButton = view.findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        return view;
    }

    private void logoutUser() {
        // Sign out user from Firebase Authentication
        FirebaseAuth.getInstance().signOut();

        // Navigate back to MainActivity
        navigateToLoginActivity();
    }

    private void navigateToLoginActivity() {
        login loginActivity = (login) requireActivity();
        loginActivity.navigateToSignIn();
    }
}