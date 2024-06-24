// AppointmentDetailsActivity.java
package com.s22010304.e_doc;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetailsActivity extends AppCompatActivity {

    TextView patientNameTextView, dateTextView, timeTextView, modeTextView, doctorUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        patientNameTextView = findViewById(R.id.PatientNameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        modeTextView = findViewById(R.id.modeTextView);
        doctorUsername = findViewById(R.id.doctorUsername);

        // Get the data from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patientNameTextView.setText(extras.getString("patientName"));
            dateTextView.setText(extras.getString("date"));
            timeTextView.setText(extras.getString("time"));
            modeTextView.setText(extras.getString("mode"));
            doctorUsername.setText(extras.getString("doctorUsername"));
        }
    }
}
