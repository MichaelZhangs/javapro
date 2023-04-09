package com.gientech.redisdemo.info;

import org.apache.catalina.User;

public class UserInfo {

    private String id;
    private String username;
    private String mobile;

    public UserInfo(String id, String username, String mobile){
        this.id = id;
        this.username  =username;
        this.mobile = mobile;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }





}
