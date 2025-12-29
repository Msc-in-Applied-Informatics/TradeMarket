package com.eshop.trademarket.model;

import javax.persistence.*;

@MappedSuperclass
public class User {
	
	@Id
	private String AFM;
	
	private String name;
	private String email;
	private String password;
	private String role;
	
	public User() {}
	
	public User(String tID, String name, String email, String password, String role) {
		AFM = tID;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAFM() {
		return AFM;
	}
	
	public void setAFM(String tID) {
		AFM = tID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
