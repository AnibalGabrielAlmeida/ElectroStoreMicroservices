package com.electrostore.productservice.service;

import com.electrostore.productservice.model.Product;
import com.electrostore.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

public class ProductService implements IProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if(existingProduct != null && product != null){
            existingProduct.setNombre(product.getNombre());
            existingProduct.setMarca(product.getMarca());
            existingProduct.setPrecio(product.getPrecio());
            productRepository.save(existingProduct);
        }
        return existingProduct;
    }
}