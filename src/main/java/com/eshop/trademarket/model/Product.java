package com.eshop.trademarket.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String type;
	private String brand;
	private String description;
	private double price;
	private int stock;
	
	@ManyToOne
	@JoinColumn(name = "shop_afm")
	@JsonIgnore
	private Shop shop;
	
	public Product() {}
	
	public Product(String type, String brand, String description, double price, int stock, Shop shop) {
		this.type = type;
		this.brand = brand;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.shop = shop;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
