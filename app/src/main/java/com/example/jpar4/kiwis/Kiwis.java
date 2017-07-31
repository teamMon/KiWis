package com.example.jpar4.kiwis;

import android.app.Application;

/**
 * Created by jpar4 on 2017-06-03.
 */

public class Kiwis extends Application {
    private String userID;
    private String userName;
    private String profilePath;

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
