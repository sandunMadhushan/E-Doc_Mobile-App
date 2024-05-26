package com.s22010304.e_doc;

import android.app.Service;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM Token", "New token: " + token);

        // Save or send the token to your server
        saveTokenToServer(token);
    }

    private void saveTokenToServer(String token) {
        // Save token to Firebase database or send it to your server
        DatabaseReference tokenRef = FirebaseDatabase.getInstance().getReference("admin_fcm_tokens").child("edoc_admin");
        tokenRef.setValue(token);
    }
}
