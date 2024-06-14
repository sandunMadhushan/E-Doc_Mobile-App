package com.s22010304.e_doc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppointmentRequestAdapter extends RecyclerView.Adapter<AppointmentRequestAdapter.ViewHolder>{

    ArrayList<AppointmentRequestsModel> list;
    Context context;

    public AppointmentRequestAdapter(ArrayList<AppointmentRequestsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_appointment_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AppointmentRequestsModel model = list.get(position);

//        Picasso.get().load(model.getDrImageUrl()).placeholder(R.drawable.baseline_person_24_primary).into(holder.drImageView);

        holder.patientNameTV.setText(model.getLoggedusername());
        holder.dateTextView.setText(model.getSelectedDate());
        holder.timeTextView.setText(model.getSelectedTime());
        holder.modeTextView.setText(model.getSelectedMode());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView drImageView, approve, decline;
        TextView dateTextView, timeTextView, modeTextView, patientNameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            modeTextView = itemView.findViewById(R.id.modeTextView);
            patientNameTV = itemView.findViewById(R.id.patientNameTV);

            approve = itemView.findViewById(R.id.approve);
            decline = itemView.findViewById(R.id.decline);
            approve.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                AppointmentRequestsModel model = list.get(position);

                DatabaseReference approvedAppointmentsRef = FirebaseDatabase.getInstance().getReference()
                        .child("approved_appointments")
                        .child(model.getDoctorName())
                        .child(model.getLoggedusername())
                        .child(model.getSelectedDate());

                approvedAppointmentsRef.child("patientUsername").setValue(model.getLoggedusername());
                approvedAppointmentsRef.child("selectedDate").setValue(model.getSelectedDate());
                approvedAppointmentsRef.child("selectedTime").setValue(model.getSelectedTime());
                approvedAppointmentsRef.child("selectedMode").setValue(model.getSelectedMode());


                DatabaseReference appointmentRef = FirebaseDatabase.getInstance().getReference()
                        .child("new_appointments")
                        .child(model.getDoctorName())
                        .child(model.getSelectedDate().split("/")[0])
                        .child(model.getSelectedDate().split("/")[1])
                        .child(model.getSelectedDate().split("/")[2])
                        .child(model.getLoggedusername());


                appointmentRef.child("status").setValue("approved");


                list.remove(position);
                notifyItemRemoved(position);
            }
        }

    }

}
