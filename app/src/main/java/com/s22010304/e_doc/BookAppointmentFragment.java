package com.s22010304.e_doc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class BookAppointmentFragment extends Fragment {

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

        // Find the initial button
        Button initialButton = view.findViewById(R.id.initialButton);

        // Find the layout containing the hidden buttons
        final LinearLayout hiddenButtonsLayout = view.findViewById(R.id.hiddenButtonsLayout);

        // Set click listener for the initial button
        initialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of the layout containing hidden buttons
                if (hiddenButtonsLayout.getVisibility() == View.VISIBLE) {
                    hiddenButtonsLayout.setVisibility(View.GONE);
                } else {
                    hiddenButtonsLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}
