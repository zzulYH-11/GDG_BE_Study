package com.example.shop.order;

import com.example.shop.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int productQuantity;
    private int price;

    public OrderProduct(Order order, Product product, int productQuantity, int price) {
        this.order = order;
        this.product = product;
        this.productQuantity = productQuantity;
        this.price = price;
    }
}
