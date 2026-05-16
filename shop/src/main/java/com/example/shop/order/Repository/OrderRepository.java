package com.example.shop.order.Repository;

import com.example.shop.order.Entity.Order;
import com.example.shop.order.Entity.OrderProduct;

import java.util.List;

public interface OrderRepository {

    Order findById(long orderId);

    List<Order> findAll();

    void saveOrder(Order order);

    void saveOrderProduct(OrderProduct orderProduct);

    List<OrderProduct> findAllProductsByOrderId(Long orderId);

    void deleteById(Long orderId);
}
