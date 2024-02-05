package com.electrostore.cartservice.service;


import com.electrostore.cartservice.dto.ProductDto;
import com.electrostore.cartservice.dto.ProductRequest;
import com.electrostore.cartservice.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IShoppingCartService {
    public void createShoppingCart(ShoppingCart shoppingCart);
    public List<ShoppingCart> getShoppingCarts();
    public ShoppingCart findShoppingCartById(Long id);
    public void deleteShoppingCart(Long id);
    public ShoppingCart updateShoppingCart(Long id, ShoppingCart shoppingCart);

    void addProductToCart(Long cartId, ProductDto productDto);
}
