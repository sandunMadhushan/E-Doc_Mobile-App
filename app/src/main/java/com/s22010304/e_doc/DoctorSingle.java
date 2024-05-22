package com.s22010304.e_doc;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorSingle extends AppCompatActivity {

    TextView nameTextView, nameTextView2, usernameTextView, SLMCRegTextView, NICTextView, SpecialAreTextView, ConNoTextView, WorkAddressTextView, homeAddressTextView;
    CircleImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_single);

        nameTextView.findViewById(R.id.nameTextView);
        usernameTextView.findViewById(R.id.usernameTextView);
        nameTextView2.findViewById(R.id.nameTextView2);
        SLMCRegTextView.findViewById(R.id.SLMCRegTextView);
        NICTextView.findViewById(R.id.NICTextView);
        SpecialAreTextView.findViewById(R.id.SpecialAreTextView);
        ConNoTextView.findViewById(R.id.ConNoTextView);
        WorkAddressTextView.findViewById(R.id.WorkAddressTextView);
        homeAddressTextView.findViewById(R.id.homeAddressTextView);

        img1.findViewById(R.id.img1);


    }
}