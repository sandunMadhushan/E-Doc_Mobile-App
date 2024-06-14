package com.s22010304.e_doc;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010304.e_doc.databinding.FragmentAppointmentsBinding;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AppointmentsFragment extends Fragment {
    private static final String TAG = "AppointmentsFragment";
    RecyclerView recyclerView;
    ArrayList<Appointment> recycleList;
    FirebaseDatabase firebaseDatabase;
    private FragmentAppointmentsBinding binding;
    private String loggedInUsername;

    public AppointmentsFragment() {}

    public static AppointmentsFragment newInstance(String userName, String profilePictureUri, String name) {
        AppointmentsFragment fragment = new AppointmentsFragment();
        Bundle args = new Bundle();
        args.putString("userName", userName);
        args.putString("profilePictureUri", profilePictureUri);
        args.putString("name", name);

        Log.d(TAG, "newInstance: userName: " + userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.rv;
        recycleList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        NewAppointmentAdapter recycleAdapter = new NewAppointmentAdapter(recycleList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recycleAdapter);

        // Get the logged-in username from the arguments
        Bundle args = getArguments();
        if (args != null) {
            loggedInUsername = args.getString("userName");
            Log.d(TAG, "Logged in username: " + loggedInUsername); // Ensure this log is shown
        }

        // Query the database for appointments of the logged-in user
        firebaseDatabase.getReference().child("new_appointments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot yearSnapshot : doctorSnapshot.getChildren()) {
                        for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                            for (DataSnapshot dateSnapshot : monthSnapshot.getChildren()) {
                                for (DataSnapshot appointmentSnapshot : dateSnapshot.getChildren()) {
                                    Appointment appointment = appointmentSnapshot.getValue(Appointment.class);
                                    if (appointment != null) {
                                        Log.d(TAG, "Appointment found: " + appointment.getloggedusername());
                                        if (appointment.getloggedusername() != null && appointment.getloggedusername().equals(loggedInUsername)) {
                                            recycleList.add(appointment);
                                            Log.d(TAG, "Appointment added: " + appointment.toString());
                                        }
                                    } else {
                                        Log.d(TAG, "Appointment is null for snapshot: " + appointmentSnapshot.getKey());
                                    }
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

    private String formatDateString(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateString);
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString; // Fallback to the original string if parsing fails
    }

    private void loadBookAppointmentFragment() {
        BookAppointmentFragment bookAppointmentFragment = new BookAppointmentFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.appointmentsfragment, bookAppointmentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
