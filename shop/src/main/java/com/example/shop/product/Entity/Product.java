package com.example.shop.product.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private int quantity;

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void updateInfo(String name, int price, int quantity) {
        if(name != null) {
            this.name = name;
        }
        if(price > 0) {
            this.price = price;
        }
        if(quantity > 0) {
            this.quantity = quantity;
        }

    }
}
