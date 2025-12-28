package com.eshop.trademarket.repository;

import com.eshop.trademarket.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    Optional<CustomUser> findByUsername(String username);

}
