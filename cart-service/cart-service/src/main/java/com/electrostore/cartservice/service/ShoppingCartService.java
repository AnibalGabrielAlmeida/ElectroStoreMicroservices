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
  public void addProductToCart(Long cartId, ProductDto productDto) {
      // Get the existing shopping cart
      ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
              .orElseThrow(() -> new RuntimeException("Cart not found"));

      // Check if the product already exists in the cart
      Long productId = productDto.getId();
      if (shoppingCart.getProductQuantityMap().containsKey(productId)) {
          // If the product is already in the cart, update its quantity
          int currentQuantity = shoppingCart.getProductQuantityMap().get(productId);
          int newQuantity = currentQuantity + productDto.getQuantity();
          shoppingCart.getProductQuantityMap().put(productId, newQuantity);
      } else {
          // If the product is not in the cart, add it with its quantity
          shoppingCart.getProductQuantityMap().put(productId, productDto.getQuantity());
      }

      // Update the total price
      double productTotalPrice = productDto.getPrice() * productDto.getQuantity();
      shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + productTotalPrice);

      // Save the updated cart in the database
      shoppingCartRepository.save(shoppingCart);
  }


    @Override
    public void removeProductFromCart(Long cartId, Long productId) {
        // Obtener el carrito de compras existente
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Verificar si el producto existe en el carrito
        if (shoppingCart.getProductQuantityMap().containsKey(productId)) {
            // Obtener la cantidad actual del producto en el carrito
            int currentQuantity = shoppingCart.getProductQuantityMap().get(productId);

            // Obtener el precio del producto del servicio de productos
            ProductDto productDto = productServiceClient.getProductInfo(productId);
            double productPrice = productDto.getPrice();

            // Calcular el precio total a restar
            double totalPriceToRemove = currentQuantity * productPrice;

            // Remover el producto del carrito
            shoppingCart.getProductQuantityMap().remove(productId);

            // Actualizar el precio total del carrito
            shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - totalPriceToRemove);

            // Guardar el carrito actualizado en la base de datos
            shoppingCartRepository.save(shoppingCart);
        }
    }
    @Override
    public void removeQuantityOfProductFromCart(Long cartId, Long productId, int quantityToRemove) {
        // Obtener el carrito de compras existente
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Verificar si el producto existe en el carrito
        if (shoppingCart.getProductQuantityMap().containsKey(productId)) {
            // Obtener la cantidad actual del producto en el carrito
            int currentQuantity = shoppingCart.getProductQuantityMap().get(productId);

            // Verificar si la cantidad a eliminar es menor o igual a la cantidad actual
            if (quantityToRemove <= currentQuantity) {
                // Obtener el precio del producto del servicio de productos
                ProductDto productDto = productServiceClient.getProductInfo(productId);
                double productPrice = productDto.getPrice();

                // Calcular el precio total a restar
                double totalPriceToRemove = productPrice * quantityToRemove;

                // Reducir la cantidad del producto en el carrito
                int newQuantity = currentQuantity - quantityToRemove;
                if (newQuantity > 0) {
                    shoppingCart.getProductQuantityMap().put(productId, newQuantity);
                } else {
                    shoppingCart.getProductQuantityMap().remove(productId);
                }

                // Actualizar el precio total del carrito
                shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - totalPriceToRemove);

                // Guardar el carrito actualizado en la base de datos
                shoppingCartRepository.save(shoppingCart);
            } else {
                throw new RuntimeException("Quantity to remove exceeds current quantity in the cart.");
            }
        } else {
            throw new RuntimeException("Product not found in the cart.");
        }
    }

}


