package com.eshop.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.trademarket.model.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String> { }
