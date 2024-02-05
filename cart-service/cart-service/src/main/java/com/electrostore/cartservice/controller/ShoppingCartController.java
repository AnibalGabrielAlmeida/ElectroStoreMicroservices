package com.electrostore.cartservice.controller;

import com.electrostore.cartservice.dto.ProductDto;
import com.electrostore.cartservice.dto.ProductRequest;
import com.electrostore.cartservice.model.ShoppingCart;
import com.electrostore.cartservice.repository.ProductServiceClient;
import com.electrostore.cartservice.service.IShoppingCartService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteCart(@PathVariable Long id){
        shoppingCartService.deleteShoppingCart(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ShoppingCart> updateCart (@PathVariable Long id,
                                     @RequestBody ShoppingCart updatedCart){
        ShoppingCart shoppingCart = shoppingCartService.updateShoppingCart(id, updatedCart);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/getProduct/{id}")
    public ProductDto getProductInfo(@PathVariable("id") Long id){
        return productServiceClient.getProductInfo(id);

    }

    @PostMapping("/{cartId}/add-product")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long cartId, @RequestBody ProductRequest request) {
        // Obtener información del producto desde el servicio de productos utilizando Feign
        ProductDto productDto = productServiceClient.getProductInfo(request.getProductId());

        // Actualizar la cantidad en el producto DTO
        productDto.setQuantity(request.getQuantity());

        // Pasar DTO para agregar el producto al carrito
        shoppingCartService.addProductToCart(cartId, productDto);

        return ResponseEntity.ok().build();
    }

}
