package com.s22010304.e_doc;

public class TopDoctorSingleModel {

    String name, specialArea;

    public TopDoctorSingleModel(String name, String specialAre) {
        this.name = name;
        this.specialArea = specialAre;
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

    public void setSpecialArea(String specialAre) {
        this.specialArea = specialAre;
    }
}
