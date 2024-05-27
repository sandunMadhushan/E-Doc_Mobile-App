package com.s22010304.e_doc;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileViewDetails extends AppCompatActivity {
    private DatabaseReference doctorsDetailsRef;

    TextView nameTextView, nameTextView2, usernameTextView, emailTextView, SLMCRegTextView, NICTextView, SpecialAreTextView, ConNoTextView, WorkAddressTextView, homeAddressTextView;
    CircleImageView img1;

    Button checkBtn, approveBtn;
    ConstraintLayout back_btn;
    ImageView copy_icon;

    private String username;
    String iurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_view_details);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");


//        username = UserManager.getInstance().getUsername();

        nameTextView = findViewById(R.id.nameTextView);
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        nameTextView2 = findViewById(R.id.nameTextView2);
        SLMCRegTextView = findViewById(R.id.SLMCRegTextView);
        NICTextView = findViewById(R.id.NICTextView);
        SpecialAreTextView = findViewById(R.id.SpecialAreTextView);
        ConNoTextView = findViewById(R.id.ConNoTextView);
        WorkAddressTextView = findViewById(R.id.WorkAddressTextView);
        homeAddressTextView = findViewById(R.id.homeAddressTextView);

        img1 = findViewById(R.id.img1);

        back_btn = findViewById(R.id.back_btn);


        doctorsDetailsRef = FirebaseDatabase.getInstance().getReference("doctors_details");

        fetchUserData();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorProfileViewDetails.this, MainActivity.class);
                intent.putExtra("userName",username);
                intent.putExtra("profilePictureUri",iurl);
                startActivity(intent);
                finish();
                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.doctorviewdetails, new DoctorHomeFragment())
                        .commit();*/
            }
        });


    }


    private void fetchUserData() {
        doctorsDetailsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DoctorDetailsModeltoSingle doctorDetailsModeltoSingle = dataSnapshot.getValue(DoctorDetailsModeltoSingle.class);
                if (doctorDetailsModeltoSingle != null) {
                    nameTextView.setText(doctorDetailsModeltoSingle.name);
                    nameTextView2.setText(doctorDetailsModeltoSingle.name);
                    emailTextView.setText(doctorDetailsModeltoSingle.email);
                    usernameTextView.setText(doctorDetailsModeltoSingle.username);
                    SLMCRegTextView.setText(doctorDetailsModeltoSingle.slmcNo);
                    NICTextView.setText(doctorDetailsModeltoSingle.nic);
                    ConNoTextView.setText(doctorDetailsModeltoSingle.contactNo);
                    WorkAddressTextView.setText(doctorDetailsModeltoSingle.workAddress);
                    homeAddressTextView.setText(doctorDetailsModeltoSingle.homeAddress);
                    SpecialAreTextView.setText(doctorDetailsModeltoSingle.specialArea);

                    // Load image using Glide or Picasso
                     iurl = doctorDetailsModeltoSingle.iurl;
                    if (iurl != null && !iurl.isEmpty()) {
                        Glide.with(DoctorProfileViewDetails.this)
                                .load(iurl)
                                .placeholder(R.drawable.baseline_person_24_lavendar)
                                .error(R.drawable.baseline_person_24_primary)
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        img1.setImageDrawable(resource);
                                        // Set image URL as tag
                                        img1.setTag(iurl);
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