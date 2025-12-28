package com.eshop.trademarket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizeController {
	
    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }
    
	@GetMapping("/status")
	public String status() {
		return "Response 200";
	}
}
