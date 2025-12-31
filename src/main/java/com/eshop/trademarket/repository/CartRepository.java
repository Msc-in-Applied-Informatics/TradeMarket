package com.eshop.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.trademarket.model.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer> { }
