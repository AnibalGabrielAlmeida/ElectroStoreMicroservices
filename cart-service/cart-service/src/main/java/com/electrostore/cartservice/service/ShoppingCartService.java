package com.electrostore.cartservice.service;

import com.electrostore.cartservice.dto.ProductDto;
import com.electrostore.cartservice.model.ShoppingCart;
import com.electrostore.cartservice.repository.ProductServiceClient;
import com.electrostore.cartservice.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService implements IShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductServiceClient productServiceClient;

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
    public ShoppingCart updateShoppingCart(Long id, ShoppingCart shoppingCart) {
        ShoppingCart existingCart = shoppingCartRepository.findById(id).orElse(null);
        if (existingCart != null && shoppingCart != null){
            existingCart.setTotalPrice(shoppingCart.getTotalPrice());
            existingCart.setListProductIds(shoppingCart.getListProductIds());
            createShoppingCart(existingCart);
        }
        return existingCart;
    }

    @Override
    public void addProductToCart(Long cartId, ProductDto productDto) {
        // Get the existing shopping cart
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Check if the product already exists in the cart
        if (!shoppingCart.getListProductIds().contains(productDto.getId())) {
            // If the product is not in the cart, add it
            shoppingCart.getListProductIds().add(productDto.getId());
        }

        double productTotalPrice = (productDto.getPrice() * productDto.getQuantity());

        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + productTotalPrice);

        // Save the updated cart in the database
        shoppingCartRepository.save(shoppingCart);
    }

}
