package com.electrostore.cartservice.controller;

import com.electrostore.cartservice.model.ShoppingCart;
import com.electrostore.cartservice.service.IShoppingCartService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    IShoppingCartService shoppingCartService;



}
