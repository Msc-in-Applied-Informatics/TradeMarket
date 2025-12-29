package com.eshop.trademarket.model;

import javax.persistence.*;

@Entity
public class Citizen  extends User{

	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
	private Cart cart;
	
	private String surname;
	
	public Citizen() {
        super();
        this.setRole("CITIZEN");
    }
	
	public Citizen(String AFM, String name, String surname, String email, String password, String role) {
		super(AFM, name, email, password, role);
		this.surname = surname;
		this.cart = new Cart();
	}

	public void setCart(Cart newCart) {
		cart = newCart;
	}
	
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Cart getCart() {
		return cart;
	}
}
