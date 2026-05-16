package com.example.shop.product.Repository;

import com.example.shop.product.DTO.Product;

import java.util.List;

public interface ProductRepository {

    Product findById(long productId);

    List<Product> findAll();

    Product findByName(String name);

    void save(Product product);

    void deleteById(long productId);
}
