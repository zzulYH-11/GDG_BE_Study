package com.example.shop.order.Service;

import com.example.shop.order.DTO.OrderCreateRequest;
import com.example.shop.order.Entity.Order;
import com.example.shop.order.Entity.OrderProduct;

import java.util.List;

public interface OrderService {

    void createOrder(Long memberId, OrderCreateRequest orderCreateRequest);

    List<Order> getAllOrders();

    List<OrderProduct> getOrder(Long orderId);

    void deleteOrder(Long orderId);


}
