package com.s22010304.e_doc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.s22010304.e_doc.databinding.FragmentAppointmentsBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppointmentsFragment extends Fragment {
    private FragmentAppointmentsBinding binding;

    public AppointmentsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        binding.detailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBookAppointmentFragment();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get arguments from the bundle
        Bundle args = getArguments();
        if (args != null) {
            String date = args.getString("selectedDate");
            String time = args.getString("selectedTime");
            //String mode = args.getString("selectedMode");

            TextView dateTextView = view.findViewById(R.id.textView27);
            TextView timeTextView = view.findViewById(R.id.textView28);
            //TextView modeTextView = view.findViewById(R.id.modeTextView);

            String formattedDate = formatDateString(date);

            dateTextView.setText(formattedDate);
            timeTextView.setText(time);
           // modeTextView.setText("Mode: " + mode);
        }
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
