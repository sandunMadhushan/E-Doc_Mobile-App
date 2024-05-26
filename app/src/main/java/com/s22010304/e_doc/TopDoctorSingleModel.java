package com.s22010304.e_doc;

public class TopDoctorSingleModel {

    String name, specialArea, username;


    public TopDoctorSingleModel(String name, String specialArea, String username) {
        this.name = name;
        this.specialArea = specialArea;
        this.username = username;
    }

    public TopDoctorSingleModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialArea() {
        return specialArea;
    }

    public void setSpecialArea(String specialArea) {
        this.specialArea = specialArea;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
