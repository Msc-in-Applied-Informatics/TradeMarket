package com.eshop.trademarket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.trademarket.DTO.ProductDTO;
import com.eshop.trademarket.model.Product;
import com.eshop.trademarket.service.ProductService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/product/getProducts/{shopAfm}")
	public ResponseEntity<Map<String, Object>> getProducts(@PathVariable(value="shopAfm") String shopAfm) throws Exception {
		Map<String, Object> result = productService.getProducts(shopAfm);

        int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
	@GetMapping("/product/getProduct/{id}")
	public ResponseEntity<Map<String, Object>> getProduct(@PathVariable(value="id") Long id) throws Exception {
		Map<String, Object> result = productService.getProduct(id);

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
	
	@DeleteMapping("/product/remove")
	public ResponseEntity<Map<String, Object>> removeProduct(@RequestBody ProductDTO product) throws Exception {
		Map<String, Object> result = productService.removeProduct(product);

        int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> search(
	    @RequestParam(required = false) String type,
	    @RequestParam(required = false) String brand,
	    @RequestParam(required = false) Double minPrice,
	    @RequestParam(required = false) Double maxPrice) {
	    
	    
		Map<String, Object> result = productService.searchProducts(type, brand, minPrice, maxPrice);

        int code = (int) result.get("code");
        return ResponseEntity.status(code).body(result);
	}
	
}
