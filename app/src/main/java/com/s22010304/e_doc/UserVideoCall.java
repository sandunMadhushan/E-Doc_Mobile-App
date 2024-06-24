package com.s22010304.e_doc;

public class UserVideoCall {
    String userName, userID;

    public UserVideoCall() {

    }

    public UserVideoCall(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
