package com.electrostore.cartservice.repository;

import com.electrostore.cartservice.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository <ShoppingCart, Long> {
}
