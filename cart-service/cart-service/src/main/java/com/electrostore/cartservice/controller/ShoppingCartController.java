package com.electrostore.cartservice.controller;

import com.electrostore.cartservice.model.ShoppingCart;
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


    @PostMapping("/create")
    public ResponseEntity<Void> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartService.createShoppingCart(shoppingCart);

        UriComponents uriComponents = UriComponentsBuilder.fromPath("/cart/{id}").buildAndExpand(product.getId());
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
}
