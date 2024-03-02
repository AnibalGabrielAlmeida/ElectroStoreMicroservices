package com.electrostore.cartservice.controller;

import com.electrostore.cartservice.dto.ProductDto;
import com.electrostore.cartservice.dto.ProductRequest;
import com.electrostore.cartservice.model.ShoppingCart;
import com.electrostore.cartservice.repository.ProductServiceClient;
import com.electrostore.cartservice.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    IShoppingCartService shoppingCartService;

    @Autowired
    private ProductServiceClient productServiceClient;

    @PostMapping("/create")
    public ResponseEntity<Void> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartService.createShoppingCart(shoppingCart);

        UriComponents uriComponents = UriComponentsBuilder.fromPath("/cart/{id}").buildAndExpand(shoppingCart.getId());
        URI location = uriComponents.toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/list")
    ResponseEntity<List<ShoppingCart>> listCarts(){
        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCarts();
        return ResponseEntity.ok(shoppingCarts);
    }

    @GetMapping("{id}")
    ResponseEntity<ShoppingCart> getCartById(@PathVariable Long id){
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartById(id);
        if(shoppingCart != null){
            return ResponseEntity.ok(shoppingCart);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    /*@PutMapping("/update/{id}")
    ResponseEntity<ShoppingCart> updateCart (@PathVariable Long id,
                                     @RequestBody ShoppingCart updatedCart){
        ShoppingCart shoppingCart = shoppingCartService.updateShoppingCart(id, updatedCart);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/


    @GetMapping("/getProduct/{id}")
    public ProductDto getProductInfo(@PathVariable("id") Long id){
        return productServiceClient.getProductInfo(id);

    }

    @PostMapping("/{cartId}/add-product")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long cartId, @RequestBody ProductRequest request) {
        // Obtain product information from the product service using Feign
        ProductDto productDto = productServiceClient.getProductInfo(request.getProductId());

        // Update the quantity in the product DTO
        productDto.setQuantity(request.getQuantity());

        // Pass DTO to add the product to the shopping cart
        shoppingCartService.addProductToCart(cartId, productDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            shoppingCartService.removeProductFromCart(cartId, productId);
            return ResponseEntity.ok("Product removed from cart successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to remove product from cart: " + e.getMessage());
        }
    }
    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> removeQuantityOfProductFromCart(@PathVariable Long cartId,
                                                                  @PathVariable Long productId,
                                                                  @RequestParam(name = "quantityToRemove") int quantityToRemove) {
        shoppingCartService.removeQuantityOfProductFromCart(cartId, productId, quantityToRemove);
        return ResponseEntity.ok("Product quantity removed from cart successfully.");
    }


}
