package com.example.shop.product.Service;

import com.example.shop.product.DTO.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Long createProduct(Map<String, Object> params);

    List<Product> getAllProducts();

    Product getProduct(Long productId);

    Product updateProduct(Long productId, Map<String, Object> params);

    void deleteProduct(Long productId);
}
