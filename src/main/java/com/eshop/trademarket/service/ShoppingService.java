package com.eshop.trademarket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.eshop.trademarket.model.Cart;
import com.eshop.trademarket.model.Citizen;
import com.eshop.trademarket.model.Order;
import com.eshop.trademarket.model.OrderItem;
import com.eshop.trademarket.model.Product;
import com.eshop.trademarket.repository.CartRepository;
import com.eshop.trademarket.repository.CitizenRepository;
import com.eshop.trademarket.repository.OrderItemRepository;
import com.eshop.trademarket.repository.OrderRepository;
import com.eshop.trademarket.repository.ProductRepository;

@Service
public class ShoppingService {
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired 
	private CitizenRepository citizenRepo;
	
	@Autowired
	private CartRepository  cartRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private OrderItemRepository orderItemRepo;

	
	public Map<String,Object>  addToCart(Long productId, String afm) {
		Map<String, Object> response = new HashMap<>();
		Optional<Citizen> citizen = citizenRepo.findById(afm);
		Optional<Product> product = prodRepo.findById(productId);
		
		
	
		
		try {
			if (!product.isPresent()) {
				response.put("status", "error");
				response.put("message", "Product not found");
		        response.put("code", 401);
				return response;
			}
			
			if( product.get().getStock() == 0) {
				response.put("status", "error");
				response.put("message", "Product out of stock");
		        response.put("code", 402);
		        return response;
			}
			
			if(!citizen.isPresent()) {
				response.put("status", "error");
				response.put("message", "There is no citizen with afm: " + afm);
		        response.put("code", 403);
				return response;
			}
			
			Cart cart = citizen.get().getCart();
		    cart.getProducts().add(product.get());
		    cart.setTotalPrice(cart.getTotalPrice() + product.get().getPrice());
		    
		    cartRepo.save(cart);
		   
			response.put("status", "success");
            response.put("code", 200);
            response.put("message", "Product added to cart");
            response.put("data", cart);            
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


	public Map<String,Object> purchase(String afm) {
		Map<String, Object> response = new HashMap<>();
		Optional<Citizen> citizen = citizenRepo.findById(afm);
		
		try {
			
			if (citizen.isPresent()) {
				Cart cart = citizen.get().getCart();
				
				if (cart.getProducts().isEmpty()) {
					response.put("status", "error");
		            response.put("code", 401);
		            response.put("message", "Empty cart");
		            return response;
				}
				
				Order order = new Order();
				order.setCitizen(citizen.get());
				order.setOrderDate(LocalDateTime.now());
				order.setTotalAmount(cart.getTotalPrice());
				order = orderRepo.save(order);
				
				List<OrderItem> orderItems = new ArrayList();
				
				//Check stock and change products to orderItem
				for(Product p : cart.getProducts()) {
					if(p.getStock() == 0) {
						response.put("status", "error");
			            response.put("code", 402);
			            response.put("message", "Product " + p.getBrand() + " out of stock!");
			            
			            return response;
					}
					
					p.setStock(p.getStock()- 1);
					prodRepo.save(p);
					// Create history
					OrderItem item = new OrderItem();
					item.setOrder(order);
					item.setProduct(p);
					item.setShop(p.getShop());
					item.setPriceAtPurchase(p.getPrice());
					orderItems.add(item);
				}
				
				orderItemRepo.saveAll(orderItems);
				// after purchase need to clear cart
				cart.getProducts().clear();
		        cart.setTotalPrice(0);
		        cartRepo.save(cart);
				
				
		        response.put("status", "success");
	            response.put("code", 200);
	            response.put("message", "Completed.. order with id: " + order.getId());
	            response.put("data", order);          
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
	
	public Map<String,Object> getShopSales(String shopAfm) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<OrderItem> allItems = orderItemRepo.findAll().stream()
		            .filter(item -> item.getShop().getAfm().equals(shopAfm))
		            .collect(Collectors.toList());
		   
			response.put("status", "success");
            response.put("code", 200);
            response.put("message", "History of sales");
            response.put("data", allItems);            
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
	
	public Map<String,Object> getCitizenHistory(String citizenAfm) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<Order> allOrders = orderRepo.findAll().stream()
						            .filter(order -> order.getCitizen().getAfm().equals(citizenAfm))
						            .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate())) 
						            .collect(Collectors.toList());
		   
			response.put("status", "success");
            response.put("code", 200);
            response.put("message", "History - order");
            response.put("data", allOrders);            
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


	public Map<String, Object> getCart(String afm) {
	    Map<String, Object> response = new HashMap<>();
	    Optional<Citizen> citizen = citizenRepo.findById(afm);

	    if (!citizen.isPresent()) {
	        response.put("status", "error");
	        response.put("message", "Citizen not found");
	        response.put("code", 404);
	        return response;
	    }

	    Cart cart = citizen.get().getCart();
	    
	    
	    if (cart == null) {
	        response.put("status", "error");
	        response.put("message", "No cart associated with this citizen");
	        response.put("code", 404);
	        return response;
	    }

	    response.put("status", "success");
	    response.put("code", 200);
	    response.put("data", cart);
	    return response;
	}
}
