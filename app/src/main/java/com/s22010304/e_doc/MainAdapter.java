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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.slmcNo.setText(model.getslmcNo());
        holder.username.setText(model.getUsername());

        Glide.with(holder.img.getContext())
                .load(model.getiurl()).
                placeholder(R.drawable.google_logo)
                .circleCrop()
                .error(R.drawable.ic_launcher_background)
                .into(holder.img);

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
            name = (TextView) itemView.findViewById(R.id.nameTextView);
            slmcNo = (TextView) itemView.findViewById(R.id.slmcTextView);
            username = (TextView) itemView.findViewById(R.id.usernameTextView);
            detailsBtn =(Button) itemView.findViewById(R.id.detailsBtn);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            detailsBtn.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) { // Ensure the position is valid
                MainModel clickedItem = getItem(position); // Get the clicked item
                Intent intent = new Intent(itemView.getContext(), DoctorSingle.class);
                // Pass the username to the DoctorSingle activity
                intent.putExtra("username", clickedItem.getUsername());
                // Start the activity
                itemView.getContext().startActivity(intent);

            }
        }


    }
}
