package com.eshop.trademarket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.trademarket.model.CustomUser;
import com.eshop.trademarket.service.AuthService;

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
	
	private final AuthService authService;

    public AuthorizeController(AuthService authService) {
        this.authService = authService;
    }
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> authenticate(@RequestBody  CustomUser credentials) throws Exception {
		Map<String, Object> result = authService.authenticateUser(
				credentials.getUsername(), 
				credentials.getPassword()
        );

        int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = authService.logoutUser(request, response);
	    return ResponseEntity.status((int) result.get("code")).body(result);
	}
}
