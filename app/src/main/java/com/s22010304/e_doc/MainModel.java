package com.s22010304.e_doc;

public class MainModel {

    String name, email, selectedOp;

    MainModel(){

    }
    public MainModel(String name, String courses, String email, String turl) {
        this.name = name;
        this.email = email;
        this.selectedOp = selectedOp;
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

    public String getSelectedOp() {
        return selectedOp;
    }

    public void setSelectedOp(String selectedOp) {
        this.selectedOp = selectedOp;
    }
}
