package com.example.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EntryPointGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntryPointGatewayApplication.class, args);
	}
	

}
