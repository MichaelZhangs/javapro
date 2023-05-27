package com.mywork.loan.entity;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String username;
    private String phone;

    private String sex;
    private String password;
    private String  birthday;
    private String edu; // 大学， 大专及以下，  硕士，  博士
    private String role; // 0 超级管理员 1 一级管理源 2 二级管理员 3 三级管理员  5 普通用户
    private String info; // 个人简介
    private String email;

}
