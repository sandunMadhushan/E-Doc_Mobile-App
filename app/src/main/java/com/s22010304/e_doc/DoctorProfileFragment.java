package com.s22010304.e_doc;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileFragment extends Fragment {
    private DatabaseReference usersRef;
    private DatabaseReference doctorsDetailsRef;
    private FirebaseStorage firebaseStorage;

    private TextView nameTextView, emailTextView, usernameTextView;
    private EditText addressEditText, nicEditText, slmcNoEditText, contactNoEditText, specialAreaEditText, workAddressEditText, homeAddressEditText;
    private Uri iurl;
    private CircleImageView img1;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

        username = UserManager.getInstance().getUsername();

        // Initialize views
        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        addressEditText = view.findViewById(R.id.addressEditText);
        nicEditText = view.findViewById(R.id.nicEditText);
        slmcNoEditText = view.findViewById(R.id.slmcNoEditText);
        contactNoEditText = view.findViewById(R.id.contactNoEditText);
        specialAreaEditText = view.findViewById(R.id.specialAreaEditText);
        workAddressEditText = view.findViewById(R.id.workAddressEditText);
        homeAddressEditText = view.findViewById(R.id.homeAddressEditText);
        img1 = view.findViewById(R.id.img1);

        usernameTextView.setText(username);

        // Initialize Firebase references
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        doctorsDetailsRef = FirebaseDatabase.getInstance().getReference("doctors_details");
        firebaseStorage = FirebaseStorage.getInstance();

        // Fetch user data
        fetchUserData();

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });

        // Handle form submission
        view.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    uploadProfileImageAndSubmitDetails();
                }
            }
        });

        return view;
    }

    private void UploadImage() {
        Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            iurl = data.getData();
            img1.setImageURI(iurl);
        }
    }

    private void fetchUserData() {
        usersRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                if (userModel != null) {
                    nameTextView.setText(userModel.name);
                    emailTextView.setText(userModel.email);
                    usernameTextView.setText(userModel.username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error while reading users data", databaseError.toException());
            }
        });
    }

    private void uploadProfileImageAndSubmitDetails() {
        if (iurl != null) {
            final StorageReference reference = firebaseStorage.getReference().child("doctor_pro_pics")
                    .child(System.currentTimeMillis() + "");
            reference.putFile(iurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            submitDoctorDetails(uri.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Image upload failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            submitDoctorDetails(null);
        }
    }

    private void submitDoctorDetails(String iurl) {
        String name = nameTextView.getText().toString();
        String email = emailTextView.getText().toString();
        String username = usernameTextView.getText().toString();
        String address = addressEditText.getText().toString();
        String nic = nicEditText.getText().toString();
        String slmcNo = slmcNoEditText.getText().toString();
        String contactNo = contactNoEditText.getText().toString();
        String specialArea = specialAreaEditText.getText().toString();
        String workAddress = workAddressEditText.getText().toString();
        String homeAddress = homeAddressEditText.getText().toString();

        DoctorDetailsModel doctorDetailsModel = new DoctorDetailsModel(name, email, username, address, nic, slmcNo, contactNo, specialArea, workAddress, homeAddress, iurl);
        doctorsDetailsRef.child(username).setValue(doctorDetailsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_LONG).show();
                sendNotificationToAdmin();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error occurred while updating profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotificationToAdmin() {
        DatabaseReference adminTokenRef = FirebaseDatabase.getInstance().getReference("admin_fcm_tokens").child("edoc_admin");
        adminTokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String adminFCMToken = dataSnapshot.getValue(String.class);
                    if (adminFCMToken != null) {
                        sendNotification(adminFCMToken);
                    } else {
                        Log.e("Admin Token", "Admin FCM token is null");
                    }
                } else {
                    Log.e("Admin Token", "Admin FCM token not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error reading admin FCM token", databaseError.toException());
            }
        });
    }

    private void sendNotification(String adminFCMToken) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder(adminFCMToken + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(new Random().nextInt(9999)))
                .addData("title", "New Doctor Approval Pending")
                .addData("body", "You have new doctor approval pending")
                .build());
    }

    private boolean validateFields() {
        if (addressEditText.getText().toString().isEmpty()) {
            addressEditText.setError("Address is required");
            addressEditText.requestFocus();
            return false;
        }
        if (nicEditText.getText().toString().isEmpty()) {
            nicEditText.setError("NIC is required");
            nicEditText.requestFocus();
            return false;
        }
        if (slmcNoEditText.getText().toString().isEmpty()) {
            slmcNoEditText.setError("SLMC No is required");
            slmcNoEditText.requestFocus();
            return false;
        }
        if (contactNoEditText.getText().toString().isEmpty()) {
            contactNoEditText.setError("Contact No is required");
            contactNoEditText.requestFocus();
            return false;
        }
        if (specialAreaEditText.getText().toString().isEmpty()) {
            specialAreaEditText.setError("Special Area is required");
            specialAreaEditText.requestFocus();
            return false;
        }
        if (workAddressEditText.getText().toString().isEmpty()) {
            workAddressEditText.setError("Work Address is required");
            workAddressEditText.requestFocus();
            return false;
        }
        if (homeAddressEditText.getText().toString().isEmpty()) {
            homeAddressEditText.setError("Home Address is required");
            homeAddressEditText.requestFocus();
            return false;
        }
        return true;
    }
}
