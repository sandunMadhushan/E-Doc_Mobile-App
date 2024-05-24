package com.s22010304.e_doc;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<DoctorSingleModel, MainAdapter.myViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<DoctorSingleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull DoctorSingleModel model) {
        holder.name.setText(model.getName());
        holder.slmcNo.setText(model.getslmcNo());
        holder.username.setText(model.getUsername());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView img;
        TextView name, slmcNo, username;
        Button detailsBtn;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView);
            slmcNo = itemView.findViewById(R.id.slmcTextView);
            username = itemView.findViewById(R.id.usernameTextView);
            detailsBtn = itemView.findViewById(R.id.detailsBtn);
            detailsBtn.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) { // Ensure the position is valid
                DoctorSingleModel clickedItem = getItem(position); // Get the clicked item
                Intent intent = new Intent(itemView.getContext(), DoctorSingle.class);
                // Pass the username to the DoctorSingle activity
                intent.putExtra("username", clickedItem.getUsername());
                // Start the activity
                itemView.getContext().startActivity(intent);
            }
        }


    }
}
