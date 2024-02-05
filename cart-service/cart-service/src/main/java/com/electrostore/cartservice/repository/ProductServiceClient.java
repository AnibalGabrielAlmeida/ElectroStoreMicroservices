package com.electrostore.cartservice.repository;

import com.electrostore.cartservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name="product-service")
public interface ProductServiceClient {
    @GetMapping ("/product/{id}")
    public ProductDto getProductInfo(@PathVariable ("id") Long id);
}
