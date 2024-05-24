package com.s22010304.e_doc;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
    ImageView copy_icon;

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

        copy_icon= findViewById(R.id.copy_icon);

        doctorsDetailsRef = FirebaseDatabase.getInstance().getReference("doctors_details");
        fetchUserData();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),ApproveDoctor.class);
                startActivity(intent1);
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.srilankamedicalcouncil.org/images/donotdelete/3wJrKqXdUmQPyGJC210801.php";
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                Intent chooser = Intent.createChooser(sendIntent, "Choose Your Browser");
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });


        copy_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Text",SLMCRegTextView.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                copy_icon.setImageDrawable(getResources().getDrawable(R.drawable.baseline_check_circle_24));

                Toast.makeText(DoctorSingle.this, "SLMC Reg.No. copied to clipboard", Toast.LENGTH_SHORT).show();

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