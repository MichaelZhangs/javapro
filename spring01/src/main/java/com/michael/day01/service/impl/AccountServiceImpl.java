package com.michael.day01.service.impl;

import com.michael.day01.dao.IAccountDao;
import com.michael.day01.service.IAccountService;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    public AccountServiceImpl(){
        System.out.println("对象创建了");
    }

    public void  saveAccount(){

        accountDao.saveAccount();
    }
}
