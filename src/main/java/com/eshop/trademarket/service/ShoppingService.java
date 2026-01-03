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

	
	public Map<String, Object> addToCart(Long productId, String afm) {
	    Map<String, Object> response = new HashMap<>();
	    Optional<Citizen> citizen = citizenRepo.findById(afm);
	    Optional<Product> productOpt = prodRepo.findById(productId);

	    try {
	        if (!productOpt.isPresent()) {
	            response.put("status", "error");
	            response.put("message", "Product not found");
	            response.put("code", 401);
	            return response;
	        }

	        Product product = productOpt.get();
	        
	        if (!citizen.isPresent()) {
	            response.put("status", "error");
	            response.put("message", "There is no citizen with afm: " + afm);
	            response.put("code", 403);
	            return response;
	        }

	        Cart cart = citizen.get().getCart();

	        Long currentCountInCart = cart.getProducts().stream()
	                .filter(p -> p.getId().equals(productId))
	                .count();

	  
	        if (currentCountInCart + 1 > product.getStock()) {
	            response.put("status", "error");
	            response.put("message", "Δεν υπάρχει επαρκές απόθεμα (Διαθέσιμα: " + product.getStock() + ")");
	            response.put("code", 405); 
	            return response;
	        }
	        cart.getProducts().add(product);
	        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
	        
	        cartRepo.save(cart);
	       
	        response.put("status", "success");
	        response.put("code", 200);
	        response.put("message", "Product added to cart");
	        response.put("data", cart);            
	        
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
	        List<Map<String, Object>> customItems = orderItemRepo.findAll().stream()
	            .filter(item -> item.getShop().getAfm().equals(shopAfm))
	            .map(item -> {
	                Map<String, Object> map = new HashMap<>();
	                map.put("id", item.getId());
	                map.put("priceAtPurchase", item.getPriceAtPurchase());
	                map.put("product", item.getProduct());
	                if (item.getOrder() != null && item.getOrder().getCitizen() != null) {
	                    map.put("citizenAfm", item.getOrder().getCitizen().getAfm());
	                    map.put("citizenName", item.getOrder().getCitizen().getName()+ " " + item.getOrder().getCitizen().getSurname());
	                }
	                return map;
	            })
	            .collect(Collectors.toList());

	        response.put("status", "success");
	        response.put("code", 200);
	        response.put("data", customItems);
	    } catch (Exception e) {
	        response.put("status", "error");
	        response.put("code", 500);
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


	public Map<String, Object> removeFromCart(Long productId, String afm) {
	    Map<String, Object> response = new HashMap<>();
	    Optional<Citizen> citizen = citizenRepo.findById(afm);
	    
	    try {
	        if (!citizen.isPresent()) {
	            response.put("status", "error");
	            response.put("message", "Citizen not found");
	            response.put("code", 403);
	            return response;
	        }

	        Cart cart = citizen.get().getCart();
	        List<Product> products = cart.getProducts();
	        
	        Optional<Product> productInCart = products.stream()
	                .filter(p -> p.getId().equals(productId))
	                .findFirst();

	        if (productInCart.isPresent()) {
	            Product p = productInCart.get();
	            
	            products.remove(p);
	            double newTotal = cart.getTotalPrice() - p.getPrice();
	            cart.setTotalPrice(Math.max(0, newTotal));
	            
	            cartRepo.save(cart);

	            response.put("status", "success");
	            response.put("code", 200);
	            response.put("message", "Product removed from cart");
	            response.put("data", cart);
	        } else {
	            response.put("status", "error");
	            response.put("message", "Product not found in cart");
	            response.put("code", 404);
	        }
	        
	    } catch (Exception e) {
	        response.put("status", "error");
	        response.put("code", 500);
	        response.put("message", "Internal server error");
	    }
	    
	    return response;
	}
}
