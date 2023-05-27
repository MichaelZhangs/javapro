package com.mywork.loan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;


/**
 * 用户贷款数据记录
 */


@Data
public class UserRecord {

    private String phone;
    private int amount;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Timestamp create_date;
    private String check_status; // 贷款进度：0 审核中， 1 已完成， -1已驳回
}
