package com.itheima.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private  Integer userId;
    private String userName;
    private String userAddress;
    private String userSex;
    private Date  userBirthday;

}
