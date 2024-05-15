package com.s22010304.e_doc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

public class BookAppointmentFragment extends Fragment {

    ConstraintLayout BackBtn;

    public BookAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        // Set the current date in the CalendarView
        Calendar calendar = Calendar.getInstance();
        CalendarView calendarView = view.findViewById(R.id.calendarView2);
        calendarView.setDate(calendar.getTimeInMillis());

        // Find the initial buttons
        Button initialButton = view.findViewById(R.id.initialButton);
        Button initialButton2 = view.findViewById(R.id.initialButton2);
        Button initialButton3 = view.findViewById(R.id.initialButton3);

        // Find the layout containing the hidden buttons
        final LinearLayout hiddenButtonsLayout = view.findViewById(R.id.hiddenButtonsLayout);
        final LinearLayout hiddenButtonsLayout2 = view.findViewById(R.id.hiddenButtonsLayout2);
        final LinearLayout hiddenButtonsLayout3 = view.findViewById(R.id.hiddenButtonsLayout3);

        initialButton.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    initialButton.setBackgroundResource(R.drawable.button_pressed);
                    initialButton2.setBackgroundResource(R.drawable.button_normal);
                    initialButton3.setBackgroundResource(R.drawable.button_normal);
                    hiddenButtonsLayout3.setVisibility(View.GONE);
                    hiddenButtonsLayout2.setVisibility(View.GONE);
                    hiddenButtonsLayout.setVisibility(View.VISIBLE);
                } else {
                    initialButton.setBackgroundResource(R.drawable.button_normal);
                    hiddenButtonsLayout.setVisibility(View.GONE);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });

        initialButton2.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    initialButton.setBackgroundResource(R.drawable.button_normal);
                    initialButton3.setBackgroundResource(R.drawable.button_normal);
                    initialButton2.setBackgroundResource(R.drawable.button_pressed);
                    hiddenButtonsLayout3.setVisibility(View.GONE);
                    hiddenButtonsLayout.setVisibility(View.GONE);
                    hiddenButtonsLayout2.setVisibility(View.VISIBLE);
                } else {
                    initialButton2.setBackgroundResource(R.drawable.button_normal);
                    hiddenButtonsLayout2.setVisibility(View.GONE);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });

        initialButton3.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    initialButton2.setBackgroundResource(R.drawable.button_normal);
                    initialButton3.setBackgroundResource(R.drawable.button_normal);
                    initialButton3.setBackgroundResource(R.drawable.button_pressed);
                    hiddenButtonsLayout.setVisibility(View.GONE);
                    hiddenButtonsLayout2.setVisibility(View.GONE);
                    hiddenButtonsLayout3.setVisibility(View.VISIBLE);
                } else {
                    initialButton3.setBackgroundResource(R.drawable.button_normal);
                    hiddenButtonsLayout3.setVisibility(View.GONE);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });

        // Set click listener for Back button
        BackBtn = view.findViewById(R.id.back_btn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the Appointments fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AppointmentsFragment());
                fragmentTransaction.addToBackStack(null); // Add transaction to the back stack
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
