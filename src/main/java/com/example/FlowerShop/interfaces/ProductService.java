package com.example.FlowerShop.interfaces;

import com.example.FlowerShop.model.Product;
import com.example.FlowerShop.service.AppService;

import java.util.Optional;

public interface ProductService extends AppService<Product> {
    Optional<Product> findByName(String name);
    void updateProduct(Long id, Product updatedProduct);
}
