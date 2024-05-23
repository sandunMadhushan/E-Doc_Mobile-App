package com.s22010304.e_doc;

public class DoctorSingleModel {
    String name, slmcNo;

    public DoctorSingleModel() {
    }

    public DoctorSingleModel(String name, String slmcNo) {
        this.name = name;
        this.slmcNo = slmcNo;
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
}
