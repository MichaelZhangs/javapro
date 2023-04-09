package com.gientech.consulconsumer.controller;


import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("consumer-test")
    public String test(){

        // 通过consumer 访问provider
//        String rst = restTemplate.getForObject("http://localhost:8001/pro-test", String.class);
        List<ServiceInstance> instances = this.discoveryClient.getInstances("provider");
        System.out.printf("list : " + instances);
        ServiceInstance serviceInstance = instances.get(0);
        URI uri = serviceInstance.getUri();
        System.out.printf("uri = " + uri );
        String targetUrl = uri + "/pro-test";
        String rst = restTemplate.getForObject(targetUrl, String.class);
        return "consumer "  + rst;
    }

}
