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

public class NewAppointmentAdapter extends RecyclerView.Adapter<NewAppointmentAdapter.ViewHolder>{

    ArrayList<Appointment> list;
    Context context;

    public NewAppointmentAdapter(ArrayList<Appointment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_appointment_single_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Appointment model = list.get(position);

//        Picasso.get().load(model.getDrImageUrl()).placeholder(R.drawable.baseline_person_24_primary).into(holder.drImageView);

        holder.drNameTextView.setText(model.getDoctorName());
        holder.drSpecialAreTextView.setText(model.getSpecialArea());
        holder.dateTextView.setText(model.getSelectedDate());
        holder.timeTextView.setText(model.getSelectedTime());
        holder.modeTextView.setText(model.getSelectedMode());
        holder.statusTextView.setText(model.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView drImageView;
        TextView drNameTextView, drSpecialAreTextView, dateTextView, timeTextView, modeTextView, statusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            drNameTextView = itemView.findViewById(R.id.drNameTextView);
            drSpecialAreTextView = itemView.findViewById(R.id.drSpecialAreTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            modeTextView = itemView.findViewById(R.id.modeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);

            drImageView = itemView.findViewById(R.id.drImageView);

        }
    }

}
