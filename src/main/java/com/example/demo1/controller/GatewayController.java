package com.example.demo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway")
public class GatewayController {
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/checked-new")
	public String check() {
		return "checked";
	}
	
	@GetMapping("/test-once")
	public String test() {
		return "Tested Properly";
		
	}

}
