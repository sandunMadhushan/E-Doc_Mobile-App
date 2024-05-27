package com.s22010304.e_doc;

public class MainModel {

    String name, email, selectedOp, slmcNo, username, iurl;

    MainModel() {

    }

    public MainModel(String name, String email, String selectedOp, String slmcNo, String username, String iurl) {
        this.name = name;
        this.email = email;
        this.selectedOp = selectedOp;
        this.slmcNo = slmcNo;
        this.iurl = iurl;
        this.username = username;
    }

    public String getiurl() {
        return iurl;
    }

    public void setiurl(String iurl) {
        this.iurl = iurl;
    }

    public String getslmcNo() {
        return slmcNo;
    }

    public void getslmcNo(String slmcNo) {
        this.slmcNo = slmcNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
