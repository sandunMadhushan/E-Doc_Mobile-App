package com.s22010304.e_doc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.s22010304.e_doc.databinding.FragmentAppointmentsBinding;


public class AppointmentsFragment extends Fragment {
    FragmentAppointmentsBinding binding;

    public AppointmentsFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater,container,false);
        binding.detailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookAppointmentFragment  BookAppointmentFragment = new BookAppointmentFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.appointmentsfragment,BookAppointmentFragment);
                transaction.commit();
            }
        });

        return binding.getRoot();
    }

}
