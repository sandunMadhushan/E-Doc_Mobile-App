package com.s22010304.e_doc;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorHomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AppointmentRequestsModel> recycleList;
    FirebaseDatabase firebaseDatabase;

    private String loggedInUsername;
    private String userName;
    private String profilePictureUri;
    private String name;
    private String userSelectedOp;

    public DoctorHomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_home, container, false);

        TextView userNameTextView = view.findViewById(R.id.text_user_name);
        ImageView profileImageView = view.findViewById(R.id.profileImage);

        if (getArguments() != null) {
            userName = getArguments().getString("userName");
            profilePictureUri = getArguments().getString("profilePictureUri");
            loggedInUsername = getArguments().getString("loggedInUsername");
            userSelectedOp = getArguments().getString("userSelectedOp");
//            name = getArguments().getString("name");

            // Save the user info locally
            saveUserInfoLocally(userName, profilePictureUri, loggedInUsername, name, userSelectedOp);
        }

        if (userName != null) {
            userNameTextView.setText(userName);
        } else {
            // If userName is null, display placeholder text
            userNameTextView.setText("Doctor");
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

        recyclerView = view.findViewById(R.id.rv);
        recycleList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        AppointmentRequestAdapter recycleAdapter = new AppointmentRequestAdapter(recycleList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recycleAdapter);

        // Retrieve the saved name and use it
        String savedName = getSavedName();
        if (savedName != null) {
            name = savedName;
            loadPendingAppointments(recycleAdapter);
        } else {
            // Fetch the name from Firebase
            fetchNameFromFirebaseAndLoadAppointments(recycleAdapter);
        }

        return view;
    }

    private void fetchNameFromFirebaseAndLoadAppointments(AppointmentRequestAdapter recycleAdapter) {
        firebaseDatabase.getReference().child("users").child(userName).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    name = snapshot.getValue(String.class);
                    if (name != null) {
                        saveNameToPreferences(name);
                        loadPendingAppointments(recycleAdapter);
                    } else {
                        Log.e("DoctorHomeFragment", "Name is null in Firebase");
                    }
                } else {
                    Log.e("DoctorHomeFragment", "Name not found in Firebase");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DoctorHomeFragment", "Failed to fetch name from Firebase", error.toException());
            }
        });
    }

    private void loadPendingAppointments(AppointmentRequestAdapter recycleAdapter) {
        if (name == null) {
            Log.e("DoctorHomeFragment", "Name is null");
            return; // Exit the method early if name is null
        }

        // Log the name to see its value
        Log.d("DoctorHomeFragment", "Name: " + name);

        firebaseDatabase.getReference().child("new_appointments").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recycleList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot yearSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                        for (DataSnapshot dateSnapshot : monthSnapshot.getChildren()) {
                            for (DataSnapshot appointmentSnapshot : dateSnapshot.getChildren()) {
                                AppointmentRequestsModel appointment = appointmentSnapshot.getValue(AppointmentRequestsModel.class);
                                if (appointment != null && "pending".equals(appointment.getStatus())) {
                                    recycleList.add(appointment);
                                }
                            }
                        }
                    }
                }
                recycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void saveUserInfoLocally(String userName, String profilePictureUri, String name, String loggedInUsername, String userSelectedOp) {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.putString("userName", userName);
        editor.putString("profilePictureUri", profilePictureUri);
        editor.putString("userSelectedOp", userSelectedOp);
//        editor.putString("name",name);
        editor.putString("loggedInUsername", loggedInUsername);
        Log.d("DoctorHomeFragment", "saveUserInfoLocally: " + userSelectedOp);
        editor.apply();
    }

    private void saveNameToPreferences(String name) {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.putString("name", name); // Use the key 'name' directly
        Log.d("doctorhome", "saveNameToPreferences: "+name);
        editor.apply();
    }

    private String getSavedName() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        Log.d(TAG, "getSavedName: "+name);
        return prefs.getString("name", null); // Retrieve the name using the key 'name'
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

    private void clearUserInfoLocally() {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
        editor.clear().apply();
    }

    public static DoctorHomeFragment newInstance(String userName, String profilePictureUri, String name, String userSelectedOp) {
        DoctorHomeFragment fragment = new DoctorHomeFragment();
        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri);
        args.putString("name", name);
        Log.d("dctrhomenewinstancew", "newInstance: "+ name);
        args.putString("userSelectedOp", userSelectedOp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString("userName");
            profilePictureUri = getArguments().getString("profilePictureUri");
            loggedInUsername = getArguments().getString("loggedInUsername");
            name = getArguments().getString("name");
            userSelectedOp = getArguments().getString("userSelectedOp");
        }
    }
}
