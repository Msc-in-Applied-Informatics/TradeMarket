package com.eshop.trademarket.service;
 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eshop.trademarket.model.Citizen;
import com.eshop.trademarket.model.Shop;
import com.eshop.trademarket.model.User;
import com.eshop.trademarket.repository.CitizenRepository;
import com.eshop.trademarket.repository.ShopRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    private CitizenRepository citizenRepo;

    @Autowired
    private ShopRepository shopRepo;
	

    @Override
    public UserDetails loadUserByUsername(String afm) throws UsernameNotFoundException {
    	User user = null;
    	Optional<Citizen> citizen = citizenRepo.findById(afm);
        if (citizen.isPresent()) {
        	user = citizen.get();
        }
        
        Optional<Shop> shop = shopRepo.findById(afm);
        if (shop.isPresent()) {
        	user = shop.get();
        }
    	
        if (user == null) throw new UsernameNotFoundException("User not found with AFM: " + afm);
        
        return user;
    }
}
