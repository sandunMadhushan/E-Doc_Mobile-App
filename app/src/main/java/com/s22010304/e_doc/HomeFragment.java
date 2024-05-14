package com.s22010304.e_doc;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
    private DrawerLayout drawerLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userName");
            profilePictureUri = getArguments().getString("profilePictureUri");
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        //View profileImageView = rootView.findViewById(R.id.navImage);



        // Find views by ID
        TextView userNameTextView = view.findViewById(R.id.text_user_name);
        ImageView profileImageView = view.findViewById(R.id.profileImage);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                if (drawer != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Set user name
        if (userName != null) {
            userNameTextView.setText(userName);
        } else {
            // If userName is null, display placeholder text
            userNameTextView.setText("User");
        }

        // Load profile picture using Glide library
        if (profilePictureUri != null) {
            Glide.with(requireContext()).load(Uri.parse(profilePictureUri)).into(profileImageView);
        } else {
            // If profilePictureUri is null, you can load a default image or hide the ImageView
            // For example, you can set a placeholder image:
            profileImageView.setImageResource(R.drawable.patient_profile_image);
        }

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

        // Clear user info from SharedPreferences
        clearUserInfoLocally();

        // Navigate back to MainActivity
        navigateToLoginActivity();
    }

    // Method to navigate back to LoginActivity
    private void navigateToLoginActivity() {
        Intent intent = new Intent(requireContext(), login.class);
        startActivity(intent);
        requireActivity().finish(); // Close the current activity to prevent navigating back to the home fragment
    }

    public static HomeFragment newInstance(String userName, String profilePictureUri) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userName");
            profilePictureUri = getArguments().getString("profilePictureUri");
        }
    }

    // Method to clear user information from SharedPreferences
    private void clearUserInfoLocally() {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.clear().apply();
    }


}