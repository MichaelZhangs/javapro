package com.mywork.loan.entity;

import lombok.Data;

@Data
public class loanAmount {


    private int amount; // 可以借贷的金额
    private String edu;
    private int age;
    public String info; // 提示的信息


}
