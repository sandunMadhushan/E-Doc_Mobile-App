package com.s22010304.e_doc;

public class DoctorSingleModel {
    String name, slmcNo, username;

    public DoctorSingleModel() {
    }

    public DoctorSingleModel(String name, String slmcNo, String username) {
        this.name = name;
        this.slmcNo = slmcNo;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getslmcNo() {
        return slmcNo;
    }

    public void setslmcNo(String slmcNo) {
        this.slmcNo = slmcNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
