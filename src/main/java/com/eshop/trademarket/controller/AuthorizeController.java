package com.eshop.trademarket.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.trademarket.model.CustomUser;

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
	
	private final AuthenticationManager authenticationManager;

    public AuthorizeController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
	
	@PostMapping("/login")
	public String authenticate(@RequestBody  CustomUser credencials) throws Exception {
		
		Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	            		credencials.getUsername(),
	            		credencials.getPassword()
	            )
	    );
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "User logged in successfully!";
	}
	
	@PostMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "Logged out successfully!";
	}
}
