package com.electrostore.salesservice.repository;

import com.electrostore.salesservice.dto.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient("cart-service")
public interface ShoppingCartServiceClient {
    @GetMapping("/cart/{id}")
    public CartDto getCartInfo(@PathVariable("id") Long id);
}
