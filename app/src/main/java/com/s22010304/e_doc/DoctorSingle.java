package com.s22010304.e_doc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorSingle extends AppCompatActivity {
    private DatabaseReference doctorsDetailsRef;

    TextView nameTextView, nameTextView2, usernameTextView, emailTextView, SLMCRegTextView, NICTextView, SpecialAreTextView, ConNoTextView, WorkAddressTextView, homeAddressTextView;
    CircleImageView img1;

    Button checkBtn, approveBtn;
    ConstraintLayout back_btn;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_single);

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

        checkBtn = findViewById(R.id.checkBtn);
        approveBtn = findViewById(R.id.approveBtn);
        back_btn = findViewById(R.id.back_btn);

        doctorsDetailsRef = FirebaseDatabase.getInstance().getReference("doctors_details");
        fetchUserData();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),ApproveDoctor.class);
                startActivity(intent1);
            }
        });

    }


    private void fetchUserData() {
        doctorsDetailsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DoctorDetailsModel doctorDetailsModel = dataSnapshot.getValue(DoctorDetailsModel.class);
                if (doctorDetailsModel != null) {
                    nameTextView.setText(doctorDetailsModel.name);
                    emailTextView.setText(doctorDetailsModel.email);
                    usernameTextView.setText(doctorDetailsModel.username);
                    SLMCRegTextView.setText(doctorDetailsModel.slmcNo);
                    NICTextView.setText(doctorDetailsModel.nic);
                    ConNoTextView.setText(doctorDetailsModel.contactNo);
                    WorkAddressTextView.setText(doctorDetailsModel.workAddress);
                    homeAddressTextView.setText(doctorDetailsModel.homeAddress);
                    SpecialAreTextView.setText(doctorDetailsModel.specialArea);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error while reading users data", databaseError.toException());
            }
        });
    }
}