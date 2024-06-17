package com.s22010304.e_doc;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010304.e_doc.databinding.FragmentAppointmentsBinding;
import java.util.ArrayList;

public class DoctorAppointmentFragment extends Fragment {

    private static final String TAG = "DoctorAppointmentsFragment";
    RecyclerView recyclerView;
    ArrayList<ApprovedAppointment> recycleList;
    FirebaseDatabase firebaseDatabase;
    private FragmentAppointmentsBinding binding;
    private String loggedInUsername;

    public DoctorAppointmentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public static DoctorAppointmentFragment newInstance(String userName, String profilePictureUri, String name, String userSelectedOp) {
        DoctorAppointmentFragment fragment = new DoctorAppointmentFragment();
        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri);
        args.putString("name", name);
        args.putString("userSelectedOp", userSelectedOp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.rv;
        recycleList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DoctorApprovedAppointmentAdapter recycleAdapter = new DoctorApprovedAppointmentAdapter(recycleList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recycleAdapter);

        // Get the logged-in username from the arguments
        Bundle args = getArguments();
        if (args != null) {
            loggedInUsername = args.getString("userName");
            Log.d(TAG, "Logged in username: " + loggedInUsername);
        }

        fetchNameFromFirebase(new NameFetchCallback() {
            @Override
            public void onNameFetched(String name) {
                Log.d(TAG, "Logged in name: " + name);

                // Query the database for appointments of the logged-in user
                firebaseDatabase.getReference().child("approved_appointments").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot patientSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot yearSnapshot : patientSnapshot.getChildren()) {
                                for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                                    for (DataSnapshot daySnapshot : monthSnapshot.getChildren()) {
                                        ApprovedAppointment appointment = daySnapshot.getValue(ApprovedAppointment.class);
                                        if (appointment != null) {
                                            Log.d(TAG, "Appointment found: " + appointment.getPatientUsername());
                                            recycleList.add(appointment);
                                            Log.d(TAG, "Appointment added: " + appointment.toString());
                                        } else {
                                            Log.d(TAG, "Appointment is null for snapshot: " + daySnapshot.getKey());
                                        }
                                    }
                                }
                            }
                        }

                        recycleAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Appointments list size: " + recycleList.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "Database error: " + error.getMessage());
                    }
                });
            }
        });
    }

    private void fetchNameFromFirebase(NameFetchCallback callback) {
        firebaseDatabase.getReference().child("users").child(loggedInUsername).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String loggedInName = snapshot.getValue(String.class);
                    Log.d(TAG, "onDataChange: " + loggedInName);
                    if (loggedInName == null) {
                        Log.e("DoctorAppointmentFragment", "Name is null in Firebase");
                    }
                    callback.onNameFetched(loggedInName);
                } else {
                    Log.e("DoctorAppointmentFragment", "Name not found in Firebase");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DoctorAppointmentFragment", "Failed to fetch name from Firebase", error.toException());
            }
        });
    }

    interface NameFetchCallback {
        void onNameFetched(String name);
    }
}
