package com.s22010304.e_doc;

public class DoctorDetailsModel {
    public String name;
    public String email;
    public String username;
    public String address;
    public String nic;
    public String slmcNo;
    public String contactNo;

    // Default constructor required for calls to DataSnapshot.getValue(DoctorDetails.class)
    public DoctorDetailsModel() {}

    public DoctorDetailsModel(String name, String email, String username, String address, String nic, String slmcNo, String contactNo) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.address = address;
        this.nic = nic;
        this.slmcNo = slmcNo;
        this.contactNo = contactNo;
    }

    // Getters and setters if needed
}
