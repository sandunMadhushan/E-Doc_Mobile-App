package com.s22010304.e_doc;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BookAppointmentFragment extends Fragment {

    private LinearLayout[] hiddenButtonsLayouts;
    private Button[] initialButtons;
    private Button[][] timeButtons;
    private int primaryColor, blackColor;
    CalendarView calendarView;
    LinearLayout messagingBtn, videocallBtn;
    String selectedDate, selectedPhase, selectedTime, selectedMode, status;
    String loggedInUsername;

    public BookAppointmentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        Bundle args = getArguments();
        if (args != null){
            String doctorName = args.getString("doctorName", "");
            String specialArea = args.getString("specialArea", "");
        }

        initializeViews(view);

        calendarView = view.findViewById(R.id.calendarView2);
        messagingBtn = view.findViewById(R.id.messagingBtn);
        videocallBtn = view.findViewById(R.id.videocallBtn);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String curDate = String.valueOf(dayOfMonth);
                String Year = String.valueOf(year);
                String Month = String.valueOf(month + 1);

                selectedDate = (Year + "/" + Month + "/" + curDate);
            }
        });

        // Get the logged-in user's username
        getCurrentLoggedInUsername();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupListeners();
    }

    private void initializeViews(View view) {
        hiddenButtonsLayouts = new LinearLayout[]{
                view.findViewById(R.id.hiddenButtonsLayout),
                view.findViewById(R.id.hiddenButtonsLayout2),
                view.findViewById(R.id.hiddenButtonsLayout3)
        };

        initialButtons = new Button[]{
                view.findViewById(R.id.initialButton),
                view.findViewById(R.id.initialButton2),
                view.findViewById(R.id.initialButton3)
        };

        timeButtons = new Button[][]{
                {view.findViewById(R.id.button1), view.findViewById(R.id.button2), view.findViewById(R.id.button3)},
                {view.findViewById(R.id.button5), view.findViewById(R.id.button6), view.findViewById(R.id.button7)},
                {view.findViewById(R.id.button9), view.findViewById(R.id.button10), view.findViewById(R.id.button11)}
        };

        primaryColor = getResources().getColor(R.color.primary);
        blackColor = getResources().getColor(R.color.black);
    }

    private void setupListeners() {
        Calendar calendar = Calendar.getInstance();
        CalendarView calendarView = getView().findViewById(R.id.calendarView2);
        calendarView.setDate(calendar.getTimeInMillis());

        for (int i = 0; i < initialButtons.length; i++) {
            int finalI = i;
            initialButtons[i].setOnClickListener(v -> toggleInitialButton(finalI));
        }

        for (LinearLayout layout : hiddenButtonsLayouts) {
            layout.setVisibility(View.GONE);
        }

        for (Button[] buttons : timeButtons) {
            for (Button button : buttons) {
                int[] index = findButtonIndex(button);
                if (index != null) {
                    int row = index[0];
                    int col = index[1];
                    button.setOnClickListener(v -> toggleTimeButton(row, col));
                }
            }
        }

        ImageView messaginTick1 = requireView().findViewById(R.id.messaginTick1);
        ImageView messaginTick2 = requireView().findViewById(R.id.messaginTick2);
        LinearLayout messagingBtn = requireView().findViewById(R.id.messagingBtn);
        LinearLayout videocallBtn = requireView().findViewById(R.id.videocallBtn);

        messagingBtn.setOnClickListener(v -> {
            messaginTick1.setVisibility(View.VISIBLE);
            messaginTick2.setVisibility(View.GONE);
            selectedMode = "Messaging";
        });

        videocallBtn.setOnClickListener(v -> {
            messaginTick1.setVisibility(View.GONE);
            messaginTick2.setVisibility(View.VISIBLE);
            selectedMode = "Video Call";
        });

        AppCompatButton bookAppointmentBtn = requireView().findViewById(R.id.bookappointmntBtn);
        bookAppointmentBtn.setOnClickListener(v -> {
            if (selectedDate != null && selectedPhase != null && selectedTime != null && selectedMode != null) {
                // Get doctor's name and special area from arguments
                Bundle args = getArguments();
                if (args != null) {
                    String doctorName = args.getString("doctorName", "");
                    String specialArea = args.getString("specialArea", "");
                    String username = args.getString("username", "");
                    String userName = args.getString("userName", "");


                    // Sanitize the doctor's name to remove invalid characters
                    String sanitizedDoctorName = doctorName.replaceAll("[.#$\\[\\]]", "_");

                    // Get a reference to the "new_appointments" node
                    DatabaseReference newAppointmentsRef = FirebaseDatabase.getInstance().getReference("new_appointments")
                            .child(sanitizedDoctorName)
                            .child(selectedDate)
                            .child(userName);

                    // Construct the appointment object
                    Appointment appointment = new Appointment(selectedDate, selectedPhase, selectedTime, selectedMode,status);
                    appointment.setDoctorName(doctorName);
                    appointment.setSpecialArea(specialArea);
                    appointment.setusername(username);
                    appointment.setloggedusername(userName);
                    appointment.setStatus("pending");

                    // Push the appointment data to the database
                    newAppointmentsRef.setValue(appointment)
                            .addOnSuccessListener(aVoid -> {
                                String message = "Appointment Booked Successfully on " + selectedDate + " during " + selectedPhase + " at " + selectedTime + " via " + selectedMode;
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                                // Navigate to AppointmentsFragment
                                AppointmentsFragment appointmentsFragment = new AppointmentsFragment();
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_containerBA, appointmentsFragment)
                                        .addToBackStack(null)
                                        .commit();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getActivity(), "Failed to book appointment. Please try again.", Toast.LENGTH_LONG).show();
                                Log.e("Firebase", "Error booking appointment", e);
                            });
                }
            } else {
                Toast.makeText(getActivity(), "Please select date, phase, time, and mode.", Toast.LENGTH_LONG).show();
            }
        });

        ConstraintLayout backBtn = requireView().findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_containerBA, new DoctorDetails())
                .addToBackStack(null).commit());
    }

    private void toggleInitialButton(int index) {
        for (int i = 0; i < initialButtons.length; i++) {
            Button button = initialButtons[i];
            LinearLayout layout = hiddenButtonsLayouts[i];
            if (i == index) {
                button.setBackgroundResource(R.drawable.button_pressed);
                button.setTextColor(primaryColor);
                layout.setVisibility(View.VISIBLE);
                selectedPhase = button.getText().toString();
            } else {
                button.setBackgroundResource(R.drawable.button_normal);
                button.setTextColor(blackColor);
                layout.setVisibility(View.GONE);
            }
        }
    }

    private void toggleTimeButton(int row, int col) {
        for (Button[] buttons : timeButtons) {
            for (Button button : buttons) {
                button.setBackgroundResource(R.drawable.button_normal);
                button.setTextColor(blackColor);
            }
        }
        Button button = timeButtons[row][col];
        button.setBackgroundResource(R.drawable.button_pressed);
        button.setTextColor(primaryColor);
        selectedTime = button.getText().toString();
    }

    private int[] findButtonIndex(Button button) {
        for (int i = 0; i < timeButtons.length; i++) {
            for (int j = 0; j < timeButtons[i].length; j++) {
                if (timeButtons[i][j] == button) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private void getCurrentLoggedInUsername() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        loggedInUsername = snapshot.child("username").getValue(String.class);
                    } else {
                        Toast.makeText(getActivity(), "User data not found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No user logged in.", Toast.LENGTH_SHORT).show();
        }
    }


}
