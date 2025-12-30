package com.eshop.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.trademarket.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { }