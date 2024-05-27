package com.s22010304.e_doc;

public class DoctorDetailsModeltoSingle {
    public String name;
    public String email;
    public String username;
    public String address;
    public String nic;
    public String slmcNo;
    public String contactNo;
    public String specialArea;
    public String workAddress;
    public String homeAddress;
    public String iurl;

    // Default constructor required for calls to DataSnapshot.getValue(DoctorDetails.class)
    public DoctorDetailsModeltoSingle() {
    }

    public DoctorDetailsModeltoSingle(String name, String email, String username, String address, String nic, String slmcNo, String contactNo, String specialArea, String workAddress, String homeAddress, String iurl) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.address = address;
        this.nic = nic;
        this.slmcNo = slmcNo;
        this.contactNo = contactNo;
        this.specialArea = specialArea;
        this.workAddress = workAddress;
        this.homeAddress = homeAddress;
        this.iurl = iurl;
    }

    // Getters and setters if needed
}
