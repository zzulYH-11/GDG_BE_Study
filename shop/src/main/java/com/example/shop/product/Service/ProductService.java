package com.example.shop.product.Service;

import com.example.shop.product.DTO.ProductDTO;
import com.example.shop.product.DTO.ProductListDTO;

public interface ProductService {

    Long createProduct(ProductDTO productDTO);

    ProductListDTO getAllProducts();

    ProductDTO getProduct(Long productId);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    void deleteProduct(Long productId);
}
