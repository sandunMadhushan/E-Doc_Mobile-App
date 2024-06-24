package com.s22010304.e_doc;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.zegocloud.uikit.plugin.invitation.ZegoInvitationType;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppointmentDetailsActivity extends AppCompatActivity {

    TextView patientNameTextView, dateTextView, timeTextView, modeTextView, doctorUsername;
    RecyclerView recyclerView;
    String patientUserID, patientName;
    String doctorUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        patientNameTextView = findViewById(R.id.PatientNameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        modeTextView = findViewById(R.id.modeTextView);
        doctorUsername = findViewById(R.id.doctorUsername);
        recyclerView = findViewById(R.id.recycler);

        // Get the data from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patientNameTextView.setText(extras.getString("patientName"));
            dateTextView.setText(extras.getString("date"));
            timeTextView.setText(extras.getString("time"));
            modeTextView.setText(extras.getString("mode"));
            doctorUsername.setText(extras.getString("doctorUsername"));
        }

        patientName = patientNameTextView.getText().toString();

        String currentUser = "";
        if (doctorUsername.getText() != null) {
            currentUser = doctorUsername.getText().toString();
        }

        // Initialize the adapter with an empty list
        ArrayList<UserVideoCall> initialUserList = new ArrayList<>();
        UsersVideoAdapter adapter = new UsersVideoAdapter(this, initialUserList);
        recyclerView.setAdapter(adapter);

        // Fetch and set data
        fetchAndSetUserData(adapter, currentUser);

        // Set the item click listener
        adapter.setOnItemClickListener(new UsersVideoAdapter.OnItemClickListener() {
            @Override
            public void onClick(UserVideoCall user) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AppointmentDetailsActivity.this);
                View view = LayoutInflater.from(AppointmentDetailsActivity.this).inflate(R.layout.user_bottom_sheet, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

                TextView userNameTV = view.findViewById(R.id.userNameTV);
                TextView userIdTV = view.findViewById(R.id.userIdTV);

                ZegoSendCallInvitationButton voiceCallBtn = view.findViewById(R.id.voiceCallBtn);
                ZegoSendCallInvitationButton videoCallBtn = view.findViewById(R.id.videoCallBtn);

                voiceCallBtn.setIsVideoCall(false);
                voiceCallBtn.setType(ZegoInvitationType.VOICE_CALL);
                voiceCallBtn.setResourceID("zego_uikit_call");
                Log.d(TAG, "onClick: " + user.getUserID());
                Log.d(TAG, "onClick: " + user.getUserName());
                voiceCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(user.getUserID(), user.getUserName())));

                videoCallBtn.setIsVideoCall(true);
                videoCallBtn.setType(ZegoInvitationType.VIDEO_CALL);
                videoCallBtn.setResourceID("zego_uikit_call");
                videoCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(user.getUserID(), user.getUserName())));

                userNameTV.setText(MessageFormat.format("User Name: {0}", user.getUserName()));
                userIdTV.setText(MessageFormat.format("User ID: {0}", user.getUserID()));
            }
        });

        PermissionX.init(AppointmentDetailsActivity.this).permissions(Manifest.permission.SYSTEM_ALERT_WINDOW)
                .onExplainRequestReason(new ExplainReasonCallback() {
                    @Override
                    public void onExplainReason(@NonNull ExplainScope scope, @NonNull List<String> deniedList) {
                        String message = "We need your consent for the following permissions in order to use the offline call function properly";
                        scope.showRequestReasonDialog(deniedList, message, "Allow", "Deny");
                    }
                }).request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                    }
                });
    }

    private void fetchAndSetUserData(UsersVideoAdapter adapter, String currentUser) {
        long appID = getResources().getInteger(R.integer.app_id);
        String appSign = getString(R.string.app_sign);

        String patientUserName = patientNameTextView.getText().toString();
        String doctorUserName = doctorUsername.getText().toString();

        // Initialize Firebase database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        ArrayList<UserVideoCall> arrayList = new ArrayList<>();

        // Fetch patient user data
        databaseReference.child(patientUserName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    patientUserID = snapshot.child("userID").getValue(String.class);
                    UserVideoCall patientUser = new UserVideoCall(patientUserID, patientUserName);
                    arrayList.add(patientUser);

                    // Fetch doctor user data after patient data is fetched
                    databaseReference.child(doctorUserName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                doctorUserID = snapshot.child("userID").getValue(String.class);
                                UserVideoCall doctorUser = new UserVideoCall(doctorUserID, doctorUserName);
                                if (!currentUser.equals(doctorUserName)) {
                                    arrayList.add(doctorUser);
                                }

                                // Initialize ZegoUIKit for the current user
                                if (currentUser.equals(doctorUserName)) {
                                    ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
                                    ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, doctorUserID, doctorUserName, callInvitationConfig);
                                } else if (currentUser.equals(patientUserName)) {
                                    ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
                                    ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, patientUserID, patientUserName, callInvitationConfig);
                                }

                                adapter.setData(arrayList);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
