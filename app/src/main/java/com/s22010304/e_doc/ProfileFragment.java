package com.s22010304.e_doc;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class ProfileFragment extends Fragment {
    ConstraintLayout BackBtn;

    public static Fragment newInstance(String userName, String profilePictureUri, String name) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri);
        args.putString("name", name);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        BackBtn = view.findViewById(R.id.back_btn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });

        AppCompatButton backtohome;
        backtohome = view.findViewById(R.id.backtohome);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();

            }
        });


        LinearLayout appointment_history;
        appointment_history = view.findViewById(R.id.appointment_history);

        appointment_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.profilefragment, new AppointmentHistory());
                fragmentTransaction.addToBackStack(null); // Add transaction to the back stack
                fragmentTransaction.commit();
            }
        });

        LinearLayout update_profile;
        update_profile = view.findViewById(R.id.update_profile);

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ProfileDetails.class);
                startActivity(intent);
            }
        });

        LinearLayout update_location;
        update_location = view.findViewById(R.id.update_location);

        update_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LocationActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish(); // Close the current activity to prevent navigating back to the home fragment
    }
}


//previous code with drawer

/*
package com.s22010304.e_doc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private DrawerLayout drawerLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        View profileImageView = rootView.findViewById(R.id.navImage);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                if (drawer != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        return rootView;
    }
}
*/
