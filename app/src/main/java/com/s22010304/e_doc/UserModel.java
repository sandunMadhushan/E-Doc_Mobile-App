package com.s22010304.e_doc;

public class UserModel {
    public String name;
    public String email;
    public String username;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public UserModel() {}

    public UserModel(String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
    }

    // Getters and setters if needed

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
}
