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

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView drImageView;
        TextView dateTextView, timeTextView, modeTextView, patientNameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            modeTextView = itemView.findViewById(R.id.modeTextView);
            patientNameTV = itemView.findViewById(R.id.patientNameTV);

        }
    }

}
