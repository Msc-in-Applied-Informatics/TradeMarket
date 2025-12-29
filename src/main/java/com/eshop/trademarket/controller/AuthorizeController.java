package com.eshop.trademarket.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.trademarket.model.Account;
import com.eshop.trademarket.model.Citizen;
import com.eshop.trademarket.model.Shop;
import com.eshop.trademarket.model.User;
import com.eshop.trademarket.service.AuthService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	public ResponseEntity<Map<String, Object>> authenticate(@RequestBody  Account credentials) throws Exception {
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
	
	@PostMapping("/register/citizen")
	public ResponseEntity<Map<String, Object>> registerAsCitizen(@RequestBody Citizen citizen) {
		Map<String, Object> result = authService.registerAsCitizen(citizen);
	    return ResponseEntity.status((int) result.get("code")).body(result);
	}
	
	@PostMapping("/register/shop")
	public ResponseEntity<Map<String, Object>> registerAsShop(@RequestBody Shop shop) {
		Map<String, Object> result = authService.registerAsShop(shop);
	    return ResponseEntity.status((int) result.get("code")).body(result);
	}
}
