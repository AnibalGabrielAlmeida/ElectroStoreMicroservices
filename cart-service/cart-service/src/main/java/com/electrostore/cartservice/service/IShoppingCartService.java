package com.electrostore.cartservice.service;


import com.electrostore.cartservice.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IShoppingCartService {
    public void createShoppingCart(ShoppingCart shoppingCart);
    public List<ShoppingCart> getShoppingCarts();
    public ShoppingCart findShoppingCartById(Long id);
    public void deleteShoppingCart(Long id);
    public void updateShoppingCart(Long id, ShoppingCart shoppingCart);
}
