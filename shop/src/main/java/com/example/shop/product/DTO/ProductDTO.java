package com.example.shop.product.DTO;

import com.example.shop.common.message.ErrorMessage;
import com.example.shop.product.Entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductDTO (

        @NotNull(message = ErrorMessage.PRODUCT_NAME_NOT_NULL)
        String name,

        @NotNull(message = ErrorMessage.PRODUCT_PRICE_NOT_NULL)
        @Min(value = 0, message = ErrorMessage.PRODUCT_PRICE_NOT_NEGATIVE)
        int price,

        @NotNull(message = ErrorMessage.PRODUCT_QUANTITY_NOT_NULL)
        @Min(value = 1, message = ErrorMessage.PRODUCT_QUANTITY_AT_LEAST_1)
        int quantity
){
    public static ProductDTO from(Product product) {
        return new ProductDTO(product.getName(), product.getPrice(), product.getQuantity());
    }
}
