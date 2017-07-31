package com.example.jpar4.kiwis.kiwis.model;

/**
 * Created by jpar4 on 2017-06-07.
 */

public class MemberList {
    String name;
    String id;
    String pw;
    String email;
    String phone;
    String profilefile_name;

    public String getProfilefile_name() {
        return profilefile_name;
    }

    public void setProfilefile_name(String profilefile_name) {
        this.profilefile_name = profilefile_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
