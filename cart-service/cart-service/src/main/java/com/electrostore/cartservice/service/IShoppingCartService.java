package com.electrostore.cartservice.service;


import com.electrostore.cartservice.dto.ProductDto;
import com.electrostore.cartservice.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IShoppingCartService {
    public void createShoppingCart(ShoppingCart shoppingCart);
    public List<ShoppingCart> getShoppingCarts();
    public ShoppingCart findShoppingCartById(Long id);

    void addProductToCart(Long cartId, ProductDto productDto);

    void removeProductFromCart(Long cartId, Long productId);


    void removeQuantityOfProductFromCart(Long cartId, Long productId, int quantityToRemove);
}
