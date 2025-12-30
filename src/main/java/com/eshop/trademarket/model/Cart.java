package com.eshop.trademarket.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cart {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonIgnore
    @OneToOne(mappedBy = "cart")
    private Citizen citizen;

	@ManyToMany
	@JoinTable(
      name = "cart_products", 
      joinColumns = @JoinColumn(name = "cart_id"), 
      inverseJoinColumns = @JoinColumn(name = "product_id")
    )
	private List<Product> products;
	
	private double totalPrice;

	public Cart(Citizen citizen, List<Product> products, double totalPrice) {
		this.citizen = citizen;
		this.products = products;
		this.totalPrice = totalPrice;
	}
	
	public Cart() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Citizen getCitizen() {
		return citizen;
	}

	public void setCitizen(Citizen citizen) {
		this.citizen = citizen;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
