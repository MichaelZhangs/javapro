package com.itheima.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private  Integer userId;
    private String userName;
    private String userAddress;
    private String userSex;
    private Date  userBirthday;
    // 一对多关系映射

    private List<Account> accounts;

}
