package com.eshop.trademarket.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.eshop.trademarket.model.Account;
import com.eshop.trademarket.model.Cart;
import com.eshop.trademarket.model.Citizen;
import com.eshop.trademarket.model.Shop;
import com.eshop.trademarket.repository.CitizenRepository;
import com.eshop.trademarket.repository.ShopRepository;

@Service
public class AuthService {
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
	private CitizenRepository citizenRepo;
	@Autowired
	private ShopRepository shopRepo;

    public Map<String, Object> authenticateUser(String username, String password) {
        Map<String, Object> response = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            Account user = (Account) authentication.getPrincipal();
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
    
    public Map<String, Object> registerAsCitizen(Citizen citizen) {
        Map<String, Object> response = new HashMap<>();

        try {
        	if (checkIfAfmExists(citizen.getAFM())) {
                response.put("status", "error");
                response.put("code", 400);
                response.put("message", "User with this AFM already exists");
                return response;
            }
        	
        	Cart newCart = new Cart();
            citizen.setCart(newCart);
            Citizen savedCitizen = citizenRepo.save(citizen);
        	response.put("status", "success");
            response.put("code", 200);
            response.put("message", "Register successful");
            response.put("data", savedCitizen);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("code", 500);
            response.put("message", "Internal server error");
        }

        return response;
    }
    
    public Map<String, Object> registerAsShop(Shop shop) {
        Map<String, Object> response = new HashMap<>();

        try {
        	if (checkIfAfmExists(shop.getAFM())) {
                response.put("status", "error");
                response.put("code", 400);
                response.put("message", "A user or shop with this AFM already exists");
                return response;
            }
        	
        	if (shop.getProducts() == null) {
                shop.setProducts(new ArrayList<>());
            }
           
        	Shop savedShop = shopRepo.save(shop);
        	
            response.put("status", "success");
            response.put("code", 200);
            response.put("message", "Register successful");
            response.put("data", savedShop); 
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("code", 500);
            response.put("message", "Internal server error");
        }

        return response;
    }
    
    private boolean checkIfAfmExists(String afm) {
        return citizenRepo.existsById(afm) || shopRepo.existsById(afm);
    }
}
