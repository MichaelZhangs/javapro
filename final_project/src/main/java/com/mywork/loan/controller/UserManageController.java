package com.mywork.loan.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin//解决跨域
@RequestMapping("/userManage")
public class UserManageController {

    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
