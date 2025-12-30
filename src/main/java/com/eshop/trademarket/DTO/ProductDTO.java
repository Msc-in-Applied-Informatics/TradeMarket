package com.eshop.trademarket.DTO;

public class ProductDTO {
	private Long id;
	private String type;
    private String brand;
    private String description;
    private double price;
    private int stock;
    private String shopAfm;
    
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
	public String getShopAfm() {
		return shopAfm;
	}
	public void setShopAfm(String shopAfm) {
		this.shopAfm = shopAfm;
	}
	
}
