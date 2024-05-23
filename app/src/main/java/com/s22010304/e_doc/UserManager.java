package com.s22010304.e_doc;

import android.util.Log;

public class UserManager {
    private static UserManager instance;
    private String userUsername;

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUsername() {
        return userUsername;
    }
}
