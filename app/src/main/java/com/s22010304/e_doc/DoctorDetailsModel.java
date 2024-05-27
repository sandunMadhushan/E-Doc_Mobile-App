package com.s22010304.e_doc;

public class DoctorDetailsModel {
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
    public DoctorDetailsModel() {
    }

    public DoctorDetailsModel(String name, String email, String username, String address, String nic, String slmcNo, String contactNo, String specialArea, String workAddress, String homeAddress, String iurl) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getSlmcNo() {
        return slmcNo;
    }

    public void setSlmcNo(String slmcNo) {
        this.slmcNo = slmcNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getSpecialArea() {
        return specialArea;
    }

    public void setSpecialArea(String specialArea) {
        this.specialArea = specialArea;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getIurl() {
        return iurl;
    }

    public void setIurl(String iurl) {
        this.iurl = iurl;
    }
}
