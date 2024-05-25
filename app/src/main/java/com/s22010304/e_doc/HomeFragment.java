package com.s22010304.e_doc;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    private String userName;
    private String profilePictureUri;
    private String name;

    private DrawerLayout drawerLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        TextView userNameTextView = view.findViewById(R.id.text_user_name);
        ImageView profileImageView = view.findViewById(R.id.profileImage);

       /* profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                if (drawer != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });*/


        // Set user name
        if (name != null) {
            userNameTextView.setText(name);
        } else if (userName != null) {
            userNameTextView.setText(userName);}
        else {
            // If userName is null, display placeholder text

            userNameTextView.setText("User");
        }


        if (profilePictureUri != null) {
            Glide.with(requireContext()).load(Uri.parse(profilePictureUri)).into(profileImageView);
        } else {
            profileImageView.setImageResource(R.drawable.patient_profile_image);
        }

        MaterialButton logoutButton = view.findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.primary));
        logoutButton.setBackgroundTintList(colorStateList);

        // Handle clicks on doctor details buttons
        setupDoctorDetailsButtons(view);

        return view;
    }

    private void setupDoctorDetailsButtons(View view) {
        LinearLayout drpererabtn = view.findViewById(R.id.drpererabtn);
        LinearLayout drpererabtn2 = view.findViewById(R.id.drpererabtn2);

        View.OnClickListener doctorDetailsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new DoctorDetails());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };

        drpererabtn.setOnClickListener(doctorDetailsClickListener);
        drpererabtn2.setOnClickListener(doctorDetailsClickListener);
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        clearUserInfoLocally();
        navigateToLoginActivity();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(requireContext(), login.class);
        startActivity(intent);
        requireActivity().finish();
    }



    public static HomeFragment newInstance(String userName, String profilePictureUri, String name) {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri);
        args.putString("name",name);

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

    private void clearUserInfoLocally() {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.clear().apply();
    }
}