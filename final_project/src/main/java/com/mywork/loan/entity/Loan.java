package com.mywork.loan.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户贷款的审批记录
 *
 */

@Data
public class Loan {

    private String phone;
    private String username;
    private String checker_name; // 审查员 姓名
    private int amount; // 贷款金额
    private String check; // 审查： 1 通过 -1 驳回 0 审查中
    private String role; // 审查员 1 一级管理员 2 二级管理员 3 三级管理员
    Date date;         // 贷款时间
    Date check_date;   // 审批时间
    private String user_email;
    private String checker_email;
    private int user_loan_id;  // 关联 用户贷款记录的 id

}
