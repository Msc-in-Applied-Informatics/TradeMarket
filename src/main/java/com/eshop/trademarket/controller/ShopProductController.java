package com.eshop.trademarket.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.trademarket.DTO.ProductDTO;
import com.eshop.trademarket.service.ProductService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ShopProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/product")
	public ResponseEntity<Map<String, Object>> getProduct() throws Exception {
		Map<String, Object> result = productService.getProduct();

        int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
	@PostMapping("/product/addProduct")
	public ResponseEntity<Map<String, Object>> addProduct(@RequestBody ProductDTO product) throws Exception {
		Map<String, Object> result = productService.addProduct(product);

        int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
	@PutMapping("/product/update")
	public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody ProductDTO product) throws Exception {
		Map<String, Object> result = productService.updateProduct(product);

        int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
}
