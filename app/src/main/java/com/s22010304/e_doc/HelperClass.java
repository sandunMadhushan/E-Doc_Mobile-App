package com.s22010304.e_doc;

public class HelperClass {
    String name, email, username, password, selectedOp, address, age, contact_no;

    public HelperClass(String name, String email, String username, String password, String selectedOp, String address, String age, String contact_no) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.selectedOp = selectedOp;
        this.address = address;
        this.age = age;
        this.contact_no = contact_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContactno() {
        return contact_no;
    }

    public void setContactno(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getSelectedOp() {
        return selectedOp;
    }

    public void setSelectedOp(String selectedOp) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public HelperClass() {
    }
}
