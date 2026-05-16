package com.example.shop.order.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private List<ProductDto> products;

    @Getter
    @NoArgsConstructor
    public static class ProductDto {
        private String name;
        private int quantity;
        private int price;
    }
}