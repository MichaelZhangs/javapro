package com.mywork.loan.dao;

import com.mywork.loan.entity.User;


public interface UserDao {

    /*
    保存用户

     */
    void saveUser(User user);
    int judgePhone(String phone);
    User Login(String phone, String password);
    User userInfo(String  phone);

    int updateUser(User user);

}
