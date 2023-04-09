package com.gientech.consulprovider.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @GetMapping("/pro-test")
    public String test(){
        return "provider-test";
    }

}
