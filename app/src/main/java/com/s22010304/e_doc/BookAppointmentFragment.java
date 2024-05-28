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

import java.util.Calendar;

public class BookAppointmentFragment extends Fragment {

    private LinearLayout[] hiddenButtonsLayouts;
    private Button[] initialButtons;
    private Button[][] timeButtons;
    private int primaryColor, blackColor;
    CalendarView calendarView;
    LinearLayout messagingBtn, videocallBtn;
    String selectedDate, selectedPhase, selectedTime, selectedMode;

    public BookAppointmentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        initializeViews(view);

        calendarView = view.findViewById(R.id.calendarView2);
        messagingBtn = view.findViewById(R.id.messagingBtn);
        videocallBtn = view.findViewById(R.id.videocallBtn);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String curDate = String.valueOf(dayOfMonth);
                String Year = String.valueOf(year);
                String Month = String.valueOf(month+1);

                selectedDate = (Year + "/" + Month + "/" + curDate);

            }
        });




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
                String message = "Appointment Booked Successfully on " + selectedDate + " during " + selectedPhase + " at " + selectedTime + " via " + selectedMode;
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                AppointmentsFragment appointmentsFragment = new AppointmentsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("selectedDate", selectedDate);
                bundle.putString("selectedTime", selectedTime);
                bundle.putString("selectedMode", selectedMode);
                appointmentsFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_containerBA, appointmentsFragment)
                        .addToBackStack(null)
                        .commit();

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
}
