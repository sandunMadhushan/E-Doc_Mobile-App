package com.s22010304.e_doc;

import android.util.Log;
import android.view.ViewGroup;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;

public class TopDoctorAdapter extends FirebaseRecyclerAdapter<TopDoctorSingleModel, TopDoctorAdapter.myViewHolder> {
    private String userName;
    public TopDoctorAdapter(@NonNull FirebaseRecyclerOptions<TopDoctorSingleModel> options, String userName) {
        super(options);
        this.userName = userName;

    }

    @Override
    protected void onBindViewHolder(@NonNull TopDoctorAdapter.myViewHolder myViewHolder, int i, @NonNull TopDoctorSingleModel model) {
        myViewHolder.name.setText(model.getName());
        myViewHolder.specialArea.setText(model.getSpecialArea());
        myViewHolder.username.setText(model.getUsername());

        Glide.with(myViewHolder.img.getContext())
                .load(model.getiurl())
                .placeholder(R.drawable.baseline_person_24_lavendar)
                .centerCrop()
                .error(R.drawable.baseline_person_24_primary).into(myViewHolder.img);

    }

    @NonNull
    @Override
    public TopDoctorAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_doctors,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, specialArea, username;
        ShapeableImageView img;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            specialArea = itemView.findViewById(R.id.specialAreaTV);
            username = itemView.findViewById(R.id.usernameTextView);
            img = itemView.findViewById(R.id.proPicImg);
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
                intent.putExtra("userName",userName);
                // Start the activity
                itemView.getContext().startActivity(intent);

            }
        }
    }
}
