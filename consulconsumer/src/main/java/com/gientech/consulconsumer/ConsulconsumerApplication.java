package com.gientech.consulconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsulconsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulconsumerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){

		return new RestTemplate();
	}
}
