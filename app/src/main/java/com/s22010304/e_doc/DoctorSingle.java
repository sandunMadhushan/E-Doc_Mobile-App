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

public class DoctorSingle extends AppCompatActivity {
    private DatabaseReference doctorsDetailsRef;
    private DatabaseReference approvedDoctorsDetailsRef;

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

        copy_icon = findViewById(R.id.copy_icon);

        doctorsDetailsRef = FirebaseDatabase.getInstance().getReference("doctors_details");

        fetchUserData();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ApproveDoctor.class);
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


        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(nameTextView.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Approved Doctors will be visible to patients");

                builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doctorsDetailsRef = FirebaseDatabase.getInstance().getReference("doctors_details");
                        approvedDoctorsDetailsRef = FirebaseDatabase.getInstance().getReference("approved_doctors");

                        submitDoctorDetails();

                        Intent intent = new Intent(getApplicationContext(), ApproveDoctor.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DoctorSingle.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        copy_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Text", SLMCRegTextView.getText().toString());
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
                    String iurl = doctorDetailsModeltoSingle.iurl;
                    if (iurl != null && !iurl.isEmpty()) {
                        Glide.with(DoctorSingle.this)
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


    private void submitDoctorDetails() {
        String name = nameTextView.getText().toString();
        String email = emailTextView.getText().toString();
        String username = usernameTextView.getText().toString();
        String nic = NICTextView.getText().toString();
        String slmcNo = SLMCRegTextView.getText().toString();
        String contactNo = ConNoTextView.getText().toString();
        String specialArea = SpecialAreTextView.getText().toString();
        String workAddress = WorkAddressTextView.getText().toString();
        String homeAddress = homeAddressTextView.getText().toString();

        // Retrieve the image URL from Glide
        String iurl = (String) img1.getTag();

        DoctorDetailsModeltoSingle approvedDoctorDetailsModel = new DoctorDetailsModeltoSingle(name, email, username, homeAddress, nic, slmcNo, contactNo, specialArea, workAddress, homeAddress, iurl);
        approvedDoctorsDetailsRef.child(username).setValue(approvedDoctorDetailsModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Successfully added to approved_doctors, now remove from doctors_details
                            doctorsDetailsRef.child(username).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Doctor Approved and Moved to Approved Doctors", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), ApproveDoctor.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed to Remove Doctor from Pending List", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to Approve Doctor", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}