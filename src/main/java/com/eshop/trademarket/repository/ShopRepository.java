package com.eshop.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.trademarket.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> { }
