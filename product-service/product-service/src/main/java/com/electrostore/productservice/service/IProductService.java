package com.electrostore.productservice.service;

import com.electrostore.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    public void createProduct(Product product);
    public List<Product> getProduct();
    public Product findProductById(Long id);
    public void deleteProduct(Long id);
    public Product updateProduct(Long id, Product product);

    List<Product> getProductsByIds(List<Long> productIds);
}
