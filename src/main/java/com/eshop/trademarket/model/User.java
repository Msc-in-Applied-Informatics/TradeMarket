package com.eshop.trademarket.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@MappedSuperclass
public class User  implements UserDetails{
	
	@Id
	private String afm;
	private String password;
	
	private String name;
	private String email;	
	private String role;
	
	public User() {}
	
	public User(String afm, String name, String email, String password, String role) {
		this.afm = afm;
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
	
	public String getAfm() {
		return afm;
	}
	
	public void setAfm(String afm) {
		this.afm = afm;
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
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
	}

	@Override
	public String getUsername() {
		return afm;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
