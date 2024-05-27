package com.s22010304.e_doc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorDetailsSingle extends AppCompatActivity {

    private DatabaseReference approvedDoctorsDetailsRef;
    TextView nameTextView, SpecialAreTextView;
    ImageView img;
    ConstraintLayout back_btn;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_details_single);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.doctordetalssingle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
        } else {
            finish();
            return;
        }

        nameTextView = findViewById(R.id.nameTextView);
        SpecialAreTextView = findViewById(R.id.SpecialAreTextView);
        img = findViewById(R.id.img);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> onBackPressed());

        approvedDoctorsDetailsRef = FirebaseDatabase.getInstance().getReference("approved_doctors");

        fetchUserData();

        /*back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    private void fetchUserData() {
        approvedDoctorsDetailsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DoctorDetailsModel doctorDetailsModel = dataSnapshot.getValue(DoctorDetailsModel.class);
                if (doctorDetailsModel != null) {
                    nameTextView.setText(doctorDetailsModel.name);
                    SpecialAreTextView.setText(doctorDetailsModel.specialArea);

                    // Load image using Glide or Picasso
                    String iurl = doctorDetailsModel.iurl;
                    if (iurl != null && !iurl.isEmpty()) {
                        Glide.with(DoctorDetailsSingle.this)
                                .load(iurl)
                                .placeholder(R.drawable.baseline_person_24_lavendar)
                                .error(R.drawable.baseline_person_24_primary)
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        img.setImageDrawable(resource);
                                        // Set image URL as tag
                                        img.setTag(iurl);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error while reading users data", databaseError.toException());
            }
        });
    }


}