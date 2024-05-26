package com.s22010304.e_doc;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class AdminHome extends Fragment {

    LinearLayout approvedoctor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);


        approvedoctor=view.findViewById(R.id.approvedoctor);

        approvedoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApproveDoctor.class);
                startActivity(intent);
            }
        });

        Intent intent = getActivity().getIntent();
        String adminUsername = intent.getStringExtra("adminUsername");

            // Get FCM token
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            String adminFCMToken = task.getResult();
                            // Store the FCM token in the database
                            assert adminUsername != null;
                            DatabaseReference adminTokenRef = FirebaseDatabase.getInstance()
                                    .getReference("admin_fcm_tokens")
                                    .child(adminUsername);
                            adminTokenRef.setValue(adminFCMToken)
                                    .addOnSuccessListener(aVoid -> Log.d("Admin Token", "Admin FCM token stored successfully"))
                                    .addOnFailureListener(e -> Log.e("Admin Token", "Error storing admin FCM token", e));
                        } else {
                            Log.e("Admin Token", "Unable to get FCM token for admin", task.getException());
                        }
                    });




        return view;
    }
}