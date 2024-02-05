package com.electrostore.cartservice.service;

import com.electrostore.cartservice.dto.ProductDto;
import com.electrostore.cartservice.dto.ProductRequest;
import com.electrostore.cartservice.model.ShoppingCart;
import com.electrostore.cartservice.repository.ProductServiceClient;
import com.electrostore.cartservice.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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
        // Obtener el carrito de compras existente
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Verificar si el producto ya existe en el carrito
        if (!shoppingCart.getListProductIds().contains(productDto.getId())) {
            // Si el producto no está en el carrito, agregarlo
            shoppingCart.getListProductIds().add(productDto.getId());
        }


        double productTotalPrice = (productDto.getPrice() * productDto.getQuantity());

        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + productTotalPrice);

        // Guardar el carrito actualizado en la base de datos
        shoppingCartRepository.save(shoppingCart);
    }
}
