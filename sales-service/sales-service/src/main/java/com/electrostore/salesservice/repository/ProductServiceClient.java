package com.electrostore.salesservice.repository;

import com.electrostore.salesservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@FeignClient(name="product-service")
public interface ProductServiceClient {
    @GetMapping("/product/list-by-ids")
    public List<ProductDto> getProductInfo(@RequestParam("productIds") List<Long> ids);

}