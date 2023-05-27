package com.itheima.domain;

import lombok.Data;

import java.util.List;

@Data
public class Account {
    private Integer uid;
    private Integer id;
    private double money;
    // 多对一
//    List<User> users;
    private User user;

}
