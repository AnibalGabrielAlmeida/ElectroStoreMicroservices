package com.electrostore.cartservice.service;

import com.electrostore.cartservice.model.ShoppingCart;
import com.electrostore.cartservice.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService implements IShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Override
    public void createShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCart findShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    @Override
    public void updateShoppingCart(Long id, ShoppingCart shoppingCart) {
        ShoppingCart existingCart = shoppingCartRepository.findById(id).orElse(null);
        if (existingCart != null && shoppingCart != null){
            existingCart.setTotalPrice(shoppingCart.getTotalPrice());
            existingCart.setProductIds(shoppingCart.getProductIds());
            createShoppingCart(existingCart);
        }
    }
}
