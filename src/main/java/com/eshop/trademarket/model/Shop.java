package com.eshop.trademarket.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Shop  extends User{
	private String owner;
	
	@OneToMany(mappedBy="shop")
	@JsonIgnore
	private List<Product> products;
	
	public Shop() {
        super();
        this.setRole("SHOP");
    }
	
	public Shop(String AFM, String name,String owner, String email, String password, String role) {
		super(AFM, name, email, password, role);
		this.owner= owner;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}	
	
}
