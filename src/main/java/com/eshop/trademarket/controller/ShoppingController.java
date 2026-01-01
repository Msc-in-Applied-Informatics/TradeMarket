package com.eshop.trademarket.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.trademarket.DTO.CartDTO;
import com.eshop.trademarket.DTO.CheckOutDTO;
import com.eshop.trademarket.service.ShoppingService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class ShoppingController {
	
	@Autowired
	private ShoppingService shoppingService;
	
	@PostMapping("/cart/add")
	public ResponseEntity<Map<String, Object>> addToCart(@RequestBody CartDTO cart) throws Exception {
		Map<String,Object> result = shoppingService.addToCart(cart.getProductId(),cart.getAfm());
		int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
    }
	
	@GetMapping("/cart/my-cart/{afm}")
	public ResponseEntity<Map<String, Object>> getMyCart(@PathVariable String afm) {
	    Map<String, Object> result = shoppingService.getCart(afm);
	    int code = (int) result.get("code");
	    return ResponseEntity.status(code).body(result);
	}
	
	@PostMapping("/cart/checkout")
	public ResponseEntity<Map<String, Object>> checkout(@RequestBody CheckOutDTO param) {
		Map<String, Object> result = shoppingService.purchase(param.getAfm());
		
		int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
    } 
	
	@GetMapping("/history/citizen/{afm}")
	public ResponseEntity<Map<String,Object>> getCitizenHistory(@PathVariable(value="afm")  String afm){
		Map<String, Object> result = shoppingService.getCitizenHistory(afm);
		
		int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
	@GetMapping("/shop/sales/{afm}")
	public ResponseEntity<Map<String,Object>> getShopSales(@PathVariable(value="afm")  String afm){
		Map<String, Object> result = shoppingService.getShopSales(afm);
		
		int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
}
