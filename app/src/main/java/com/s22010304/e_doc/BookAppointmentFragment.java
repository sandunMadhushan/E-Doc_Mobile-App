package com.s22010304.e_doc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        // Set the current date in the CalendarView
        Calendar calendar = Calendar.getInstance();
        CalendarView calendarView = view.findViewById(R.id.calendarView2);
        calendarView.setDate(calendar.getTimeInMillis());

        Button initialButton = view.findViewById(R.id.initialButton);
        Button initialButton2 = view.findViewById(R.id.initialButton2);
        Button initialButton3 = view.findViewById(R.id.initialButton3);

        final LinearLayout hiddenButtonsLayout = view.findViewById(R.id.hiddenButtonsLayout);
        final LinearLayout hiddenButtonsLayout2 = view.findViewById(R.id.hiddenButtonsLayout2);
        final LinearLayout hiddenButtonsLayout3 = view.findViewById(R.id.hiddenButtonsLayout3);

        //Background color change for time buttons
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);
        Button button5 = view.findViewById(R.id.button5);
        Button button6 = view.findViewById(R.id.button6);
        Button button7 = view.findViewById(R.id.button7);
        Button button9 = view.findViewById(R.id.button9);
        Button button10 = view.findViewById(R.id.button10);
        Button button11 = view.findViewById(R.id.button11);

        int primaryColor = getResources().getColor(R.color.primary);
        int blackColor = getResources().getColor(R.color.black);

        initialButton.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    initialButton.setBackgroundResource(R.drawable.button_pressed);
                    initialButton.setTextColor(primaryColor);
                    initialButton2.setTextColor(blackColor);
                    initialButton3.setTextColor(blackColor);
                    initialButton2.setBackgroundResource(R.drawable.button_normal);
                    initialButton3.setBackgroundResource(R.drawable.button_normal);
                    hiddenButtonsLayout3.setVisibility(View.GONE);
                    hiddenButtonsLayout2.setVisibility(View.GONE);
                    hiddenButtonsLayout.setVisibility(View.VISIBLE);
                } else {
//                    initialButton.setBackgroundResource(R.drawable.button_normal);
//                    initialButton.setTextColor(blackColor);
//                    hiddenButtonsLayout.setVisibility(View.GONE);
                    button1.setBackgroundResource(R.drawable.button_normal);
                    button2.setBackgroundResource(R.drawable.button_normal);
                    button3.setBackgroundResource(R.drawable.button_normal);
                    button5.setBackgroundResource(R.drawable.button_normal);
                    button6.setBackgroundResource(R.drawable.button_normal);
                    button7.setBackgroundResource(R.drawable.button_normal);
                    button9.setBackgroundResource(R.drawable.button_normal);
                    button10.setBackgroundResource(R.drawable.button_normal);
                    button11.setBackgroundResource(R.drawable.button_normal);
                    button1.setTextColor(blackColor);
                    button2.setTextColor(blackColor);
                    button3.setTextColor(blackColor);
                    button5.setTextColor(blackColor);
                    button6.setTextColor(blackColor);
                    button7.setTextColor(blackColor);
                    button9.setTextColor(blackColor);
                    button10.setTextColor(blackColor);
                    button11.setTextColor(blackColor);
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
                    initialButton2.setTextColor(primaryColor);
                    initialButton.setTextColor(blackColor);
                    initialButton3.setTextColor(blackColor);
                    initialButton.setTextColor(Color.BLACK);
                    hiddenButtonsLayout3.setVisibility(View.GONE);
                    hiddenButtonsLayout.setVisibility(View.GONE);
                    hiddenButtonsLayout2.setVisibility(View.VISIBLE);
                } else {
//                    initialButton2.setBackgroundResource(R.drawable.button_normal);
//                    initialButton2.setTextColor(blackColor);
//                    hiddenButtonsLayout2.setVisibility(View.GONE);
                    button1.setBackgroundResource(R.drawable.button_normal);
                    button2.setBackgroundResource(R.drawable.button_normal);
                    button3.setBackgroundResource(R.drawable.button_normal);
                    button5.setBackgroundResource(R.drawable.button_normal);
                    button6.setBackgroundResource(R.drawable.button_normal);
                    button7.setBackgroundResource(R.drawable.button_normal);
                    button9.setBackgroundResource(R.drawable.button_normal);
                    button10.setBackgroundResource(R.drawable.button_normal);
                    button11.setBackgroundResource(R.drawable.button_normal);
                    button1.setTextColor(blackColor);
                    button2.setTextColor(blackColor);
                    button3.setTextColor(blackColor);
                    button5.setTextColor(blackColor);
                    button6.setTextColor(blackColor);
                    button7.setTextColor(blackColor);
                    button9.setTextColor(blackColor);
                    button10.setTextColor(blackColor);
                    button11.setTextColor(blackColor);
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
                    initialButton.setBackgroundResource(R.drawable.button_normal);
                    initialButton3.setBackgroundResource(R.drawable.button_pressed);
                    initialButton3.setTextColor(primaryColor);
                    initialButton2.setTextColor(blackColor);
                    initialButton.setTextColor(blackColor);
                    initialButton.setTextColor(Color.BLACK);
                    hiddenButtonsLayout.setVisibility(View.GONE);
                    hiddenButtonsLayout2.setVisibility(View.GONE);
                    hiddenButtonsLayout3.setVisibility(View.VISIBLE);
                } else {
//                    initialButton3.setBackgroundResource(R.drawable.button_normal);
//                    initialButton3.setTextColor(blackColor);
//                    hiddenButtonsLayout3.setVisibility(View.GONE);
                    button1.setBackgroundResource(R.drawable.button_normal);
                    button2.setBackgroundResource(R.drawable.button_normal);
                    button3.setBackgroundResource(R.drawable.button_normal);
                    button5.setBackgroundResource(R.drawable.button_normal);
                    button6.setBackgroundResource(R.drawable.button_normal);
                    button7.setBackgroundResource(R.drawable.button_normal);
                    button9.setBackgroundResource(R.drawable.button_normal);
                    button10.setBackgroundResource(R.drawable.button_normal);
                    button11.setBackgroundResource(R.drawable.button_normal);
                    button1.setTextColor(blackColor);
                    button2.setTextColor(blackColor);
                    button3.setTextColor(blackColor);
                    button5.setTextColor(blackColor);
                    button6.setTextColor(blackColor);
                    button7.setTextColor(blackColor);
                    button9.setTextColor(blackColor);
                    button10.setTextColor(blackColor);
                    button11.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button1.setBackgroundResource(R.drawable.button_pressed);
                    button1.setTextColor(primaryColor);
                    button2.setTextColor(blackColor);
                    button3.setTextColor(blackColor);
                    button2.setBackgroundResource(R.drawable.button_normal);
                    button3.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button1.setBackgroundResource(R.drawable.button_normal);
                    button1.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button2.setBackgroundResource(R.drawable.button_pressed);
                    button2.setTextColor(primaryColor);
                    button1.setTextColor(blackColor);
                    button3.setTextColor(blackColor);
                    button1.setBackgroundResource(R.drawable.button_normal);
                    button3.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button2.setBackgroundResource(R.drawable.button_normal);
                    button2.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button3.setBackgroundResource(R.drawable.button_pressed);
                    button3.setTextColor(primaryColor);
                    button1.setTextColor(blackColor);
                    button2.setTextColor(blackColor);
                    button2.setBackgroundResource(R.drawable.button_normal);
                    button1.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button3.setBackgroundResource(R.drawable.button_normal);
                    button3.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button5.setBackgroundResource(R.drawable.button_pressed);
                    button5.setTextColor(primaryColor);
                    button6.setTextColor(blackColor);
                    button7.setTextColor(blackColor);
                    button6.setBackgroundResource(R.drawable.button_normal);
                    button7.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button5.setBackgroundResource(R.drawable.button_normal);
                    button5.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button6.setBackgroundResource(R.drawable.button_pressed);
                    button6.setTextColor(primaryColor);
                    button5.setTextColor(blackColor);
                    button7.setTextColor(blackColor);
                    button5.setBackgroundResource(R.drawable.button_normal);
                    button7.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button6.setBackgroundResource(R.drawable.button_normal);
                    button6.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button7.setBackgroundResource(R.drawable.button_pressed);
                    button7.setTextColor(primaryColor);
                    button6.setTextColor(blackColor);
                    button5.setTextColor(blackColor);
                    button5.setBackgroundResource(R.drawable.button_normal);
                    button6.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button7.setBackgroundResource(R.drawable.button_normal);
                    button7.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button9.setBackgroundResource(R.drawable.button_pressed);
                    button9.setTextColor(primaryColor);
                    button10.setTextColor(blackColor);
                    button11.setTextColor(blackColor);
                    button10.setBackgroundResource(R.drawable.button_normal);
                    button11.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button9.setBackgroundResource(R.drawable.button_normal);
                    button9.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button10.setBackgroundResource(R.drawable.button_pressed);
                    button10.setTextColor(primaryColor);
                    button9.setTextColor(blackColor);
                    button11.setTextColor(blackColor);
                    button9.setBackgroundResource(R.drawable.button_normal);
                    button11.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button10.setBackgroundResource(R.drawable.button_normal);
                    button10.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false; // Track button state

            @Override
            public void onClick(View v) {
                // Change background color based on button state
                if (!isClicked) {
                    button11.setBackgroundResource(R.drawable.button_pressed);
                    button11.setTextColor(primaryColor);
                    button10.setTextColor(blackColor);
                    button9.setTextColor(blackColor);
                    button10.setBackgroundResource(R.drawable.button_normal);
                    button9.setBackgroundResource(R.drawable.button_normal);

                } else {
                    button11.setBackgroundResource(R.drawable.button_normal);
                    button11.setTextColor(blackColor);
                }
                isClicked = !isClicked; // Toggle button state
            }
        });

        ImageView messaginTick1;
        ImageView messaginTick2;

        messaginTick1 = view.findViewById(R.id.messaginTick1);
        messaginTick2 = view.findViewById(R.id.messaginTick2);

        LinearLayout messagingBtn;
        LinearLayout videocallBtn;

        messagingBtn = view.findViewById(R.id.messagingBtn);
        videocallBtn = view.findViewById(R.id.videocallBtn);

        messagingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messaginTick1.setVisibility(View.VISIBLE);
                messaginTick2.setVisibility(View.GONE);
            }
        });

        videocallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messaginTick1.setVisibility(View.GONE);
                messaginTick2.setVisibility(View.VISIBLE);
            }
        });

        AppCompatButton bookAppointmentBtn;

        bookAppointmentBtn = view.findViewById(R.id.bookappointmntBtn);

        bookAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
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
                fragmentTransaction.replace(R.id.fragment_containerBA, new DoctorDetails());
                fragmentTransaction.addToBackStack(null); // Add transaction to the back stack
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
