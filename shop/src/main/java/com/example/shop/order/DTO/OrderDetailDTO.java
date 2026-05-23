package com.example.shop.order.DTO;

import com.example.shop.product.DTO.ProductListDTO;

public record OrderDetailDTO (
        OrderDTO orderDTO,
        ProductListDTO productListDTO
){
}
