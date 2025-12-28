package com.eshop.trademarket.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.eshop.trademarket.model.CustomUser;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Map<String, Object> authenticateUser(String username, String password) {
        Map<String, Object> response = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            CustomUser user = (CustomUser) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);

            response.put("status", "success");
            response.put("code", 200);
            response.put("message", "Login successful");
            response.put("data", user); 
            
        } catch (AuthenticationException e) {
            response.put("status", "error");
            response.put("code", 401);
            response.put("message", "Invalid credentials");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("code", 500);
            response.put("message", "Internal server error");
        }

        return response;
    }
    
    public Map<String, Object> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            
            result.put("status", "success");
            result.put("code", 200);
            result.put("message", "Logged out successfully");
        } else {
            result.put("status", "error");
            result.put("code", 400);
            result.put("message", "No active session found");
        }
        
        return result;
    }
}
