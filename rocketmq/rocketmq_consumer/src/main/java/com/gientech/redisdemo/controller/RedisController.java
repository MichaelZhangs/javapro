package com.gientech.redisdemo.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisController {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("redis/setvalue")
    public String setValue(){
        String key = "xiaoming";
        String value = "23";
        stringRedisTemplate.opsForValue().set(key, value);


        return "success";
    }

    @GetMapping("redis/getvalue")
    public String getValue(){
        String key = "xiaoming";

       String value = stringRedisTemplate.opsForValue().get(key);


        return "success "+ value;
    }
}
