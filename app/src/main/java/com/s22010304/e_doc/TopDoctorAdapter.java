package com.s22010304.e_doc;

import android.view.ViewGroup;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TopDoctorAdapter extends FirebaseRecyclerAdapter<TopDoctorSingleModel, TopDoctorAdapter.myViewHolder> {
    public TopDoctorAdapter(@NonNull FirebaseRecyclerOptions<TopDoctorSingleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TopDoctorAdapter.myViewHolder myViewHolder, int i, @NonNull TopDoctorSingleModel model) {
        myViewHolder.name.setText(model.getName());
        myViewHolder.specialArea.setText(model.getSpecialArea());
        myViewHolder.username.setText(model.getUsername());

    }

    @NonNull
    @Override
    public TopDoctorAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_doctors,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, specialArea, username;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            specialArea = itemView.findViewById(R.id.specialAreaTV);
            username = itemView.findViewById(R.id.usernameTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) { // Ensure the position is valid
                TopDoctorSingleModel clickedItem = getItem(position); // Get the clicked item
                Intent intent = new Intent(itemView.getContext(), DoctorDetailsSingle.class);
                // Pass the username to the DoctorSingle activity
                intent.putExtra("username", clickedItem.getUsername());
                // Start the activity
                itemView.getContext().startActivity(intent);

            }
        }
    }
}
