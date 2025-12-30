package com.eshop.trademarket.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.eshop.trademarket.DTO.ProductDTO;
import com.eshop.trademarket.model.Product;
import com.eshop.trademarket.model.Shop;
import com.eshop.trademarket.repository.ProductRepository;
import com.eshop.trademarket.repository.ShopRepository;

@Service
public class ProductService {
	@Autowired
	private ShopRepository shopRepo;
	@Autowired
	private ProductRepository prodRepo;
	
	public Map<String,Object> getProducts(String shopAfm){
		Map<String, Object> response = new HashMap<>();
		try {
			List<Product> products = new ArrayList();
			for(Product prod : prodRepo.findAll()) {
				if (prod.getShop().getAfm().equals(shopAfm)) products.add(prod);
			}
			response.put("status", "success");
            response.put("code", 200);
            response.put("message", "All the products are here");
            response.put("data", products);
		} catch (AuthenticationException e) {
            response.put("status", "error");
            response.put("code", 400);
            response.put("message", "Bad request");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("code", 500);
            response.put("message", "Internal server error");
        }
		return response;		
	}
	
	public Map<String,Object> getProduct(Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Optional<Product> product = prodRepo.findById(id);
			response.put("status", "success");
            response.put("code", 200);
            response.put("message", "There is product with id" + id.toString());
            response.put("data", product);
		} catch (AuthenticationException e) {
            response.put("status", "error");
            response.put("code", 400);
            response.put("message", "Bad request");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("code", 500);
            response.put("message", "Internal server error");
        }
		return response;
	}
	
	public  Map<String, Object> addProduct(ProductDTO p){
		Map<String, Object> response = new HashMap<>();
		 
	   	try {  
	   			if(!productIsExist(p)) {
			 		Optional<Shop> shopOpt = shopRepo.findById(p.getShopAfm());
			 		Product product = new Product(p.getType(),p.getBrand(),p.getDescription(),p.getPrice(),p.getStock(),shopOpt.get());
			 		prodRepo.save(product);
			 		
		            response.put("status", "success");
		            response.put("code", 200);
		            response.put("message", "Product added");
		            response.put("data", p); 	
			 	}else {
			 	     response.put("status", "error");
		            response.put("code", 401);
		            response.put("message", "Product already in list");
		            response.put("data", p); 
			 	}
        } catch (AuthenticationException e) {
            response.put("status", "error");
            response.put("code", 400);
            response.put("message", "Bad request");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("code", 500);
            response.put("message", "Internal server error");
        }
	   	return response;
	}
	
	public Map<String,Object> updateProduct(ProductDTO p){
		Map<String, Object> response = new HashMap<>();
		
	  	try {  
	  		Optional<Product> byId = prodRepo.findById(p.getId());
	  		if (byId.isPresent()) {
	  			Product existingProduct = byId.get();
	  			if (!existingProduct.getShop().getAfm().equals(p.getShopAfm())) {
	  				response.put("status", "error");
	                response.put("code", 403);
	                response.put("message", "You cannot update a product that belongs to another shop!");
	                return response;
	  			}
	  			
		 		existingProduct.setType(p.getType());
	            existingProduct.setBrand(p.getBrand());
	            existingProduct.setDescription(p.getDescription());
	            existingProduct.setPrice(p.getPrice());
	            existingProduct.setStock(p.getStock());
		 		prodRepo.save(existingProduct);
		 		
	            response.put("status", "success");
	            response.put("code", 200);
	            response.put("message", "Product updated");
	            response.put("data", p); 	
		 	}else {
		 		response.put("status", "error");
	            response.put("code", 401);
	            response.put("message", "Product not found with ID: " + p.getId());
	            response.put("data", p); 
		 	}
	    } catch (AuthenticationException e) {
	        response.put("status", "error");
	        response.put("code", 400);
	        response.put("message", "Bad request");
	    } catch (Exception e) {
	        response.put("status", "error");
	        response.put("code", 500);
	        response.put("message", "Internal server error");
	    }
		return response;
	}
	
	public Map<String,Object> removeProduct(ProductDTO p){
		Map<String, Object> response = new HashMap<>();
		try {  
	  		Optional<Product> byId = prodRepo.findById(p.getId());
	  		if (byId.isPresent()) {
	  			Product existingProduct = byId.get();
	  			if (!existingProduct.getShop().getAfm().equals(p.getShopAfm())) {
	  				response.put("status", "error");
	                response.put("code", 403);
	                response.put("message", "You cannot remove a product that belongs to another shop!");
	                return response;
	  			}
		 		prodRepo.delete(existingProduct);
		 		
	            response.put("status", "success");
	            response.put("code", 200);
	            response.put("message", "Product deleted");
	            response.put("data", p); 	
		 	}else {
		 		response.put("status", "error");
	            response.put("code", 401);
	            response.put("message", "Product not found with ID: " + p.getId());
	            response.put("data", p); 
		 	}
	    } catch (AuthenticationException e) {
	        response.put("status", "error");
	        response.put("code", 400);
	        response.put("message", "Bad request");
	    } catch (Exception e) {
	        response.put("status", "error");
	        response.put("code", 500);
	        response.put("message", "Internal server error");
	    }
		
		return response;
	}
	
	public Map<String, Object> searchProducts(String type, String brand, Double minPrice, Double maxPrice) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Product> allProducts = prodRepo.findAll();
			List<Product> searchProd = new ArrayList();
			searchProd = allProducts.stream().filter(p -> (type == null || type.isEmpty() || p.getType().equalsIgnoreCase(type)))
						        .filter(p -> (brand == null || brand.isEmpty() || p.getBrand().equalsIgnoreCase(brand)))
						        .filter(p -> (minPrice == null || p.getPrice() >= minPrice))
						        .filter(p -> (maxPrice == null || p.getPrice() <= maxPrice))
						        .collect(Collectors.toList());
			
			response.put("status", "success");
            response.put("code", 200);
            response.put("message", "Look for products with options");
            response.put("data", searchProd);
		} catch (AuthenticationException e) {
            response.put("status", "error");
            response.put("code", 400);
            response.put("message", "Bad request");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("code", 500);
            response.put("message", "Internal server error");
        }

		return response;
	}

	private boolean productIsExist(ProductDTO p) {
		List<Product> allProducts = prodRepo.findAll();
		return allProducts.stream().anyMatch(product -> 
	        product.getType().equalsIgnoreCase(p.getType()) &&
	        product.getBrand().equalsIgnoreCase(p.getBrand())
	    );
	}


	
	
	
}
