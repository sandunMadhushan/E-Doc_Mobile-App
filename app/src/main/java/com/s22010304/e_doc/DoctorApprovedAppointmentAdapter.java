package com.s22010304.e_doc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoctorApprovedAppointmentAdapter extends RecyclerView.Adapter<DoctorApprovedAppointmentAdapter.ViewHolder>{

    ArrayList<ApprovedAppointment> list;
    Context context;

    public DoctorApprovedAppointmentAdapter(ArrayList<ApprovedAppointment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.doctor_appointment_single,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ApprovedAppointment model = list.get(position);

//        Picasso.get().load(model.getDrImageUrl()).placeholder(R.drawable.baseline_person_24_primary).into(holder.drImageView);


        holder.dateTextView.setText(model.getSelectedDate());
        holder.timeTextView.setText(model.getSelectedTime());
        holder.modeTextView.setText(model.getSelectedMode());
        holder.patientNameTextView.setText(model.getPatientUsername());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView drImageView;
        TextView patientNameTextView, dateTextView, timeTextView, modeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            patientNameTextView = itemView.findViewById(R.id.PatientNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            modeTextView = itemView.findViewById(R.id.modeTextView);

            drImageView = itemView.findViewById(R.id.drImageView);

        }
    }

}
