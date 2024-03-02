package com.electrostore.productservice.service;

import com.electrostore.productservice.model.Product;
import com.electrostore.productservice.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
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
    public Product updateProduct(Long id, Product updatedProduct) {
        // Retrieve the existing product from the repository using its ID
        Product existingProduct = productRepository.findById(id).orElse(null);
        // Check if both existing product and updated product are not null
        if (existingProduct != null && updatedProduct != null) {
            // Copy non-null properties from the updated product to the existing product
            existingProduct = copyNonNullProperties(updatedProduct, existingProduct);
            // Save the updated existing product to the repository
            productRepository.save(existingProduct);
        }
        // Return the existing product (updated or not)
        return existingProduct;
    }

    // Method to copy non-null properties from a source object to a target object
    private Product copyNonNullProperties(Product source, Product target) {
        // Use BeanUtils.copyProperties() from Spring Framework to copy non-null properties
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        // Return the modified target object
        return target;
    }

    // Method to get the names of null properties of an object
    private String[] getNullPropertyNames(Object source) {
        // Create a BeanWrapper for the source object
        final BeanWrapper src = new BeanWrapperImpl(source);
        // Get all properties of the source object
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        // Create a set to store the names of null properties
        Set<String> emptyNames = new HashSet<>();
        // Iterate over all properties of the source object
        for (PropertyDescriptor pd : pds) {
            // Get the value of the current property
            Object srcValue = src.getPropertyValue(pd.getName());
            // If the value of the property is null, add the property name to the set
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        // Convert the set of null property names into an array of strings
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    @Override
    public List<Product> getProductsByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }
}