package com.example.shop.order.Service;

import com.example.shop.order.DTO.OrderDetailDTO;
import com.example.shop.order.DTO.OrderListDTO;
import com.example.shop.order.Entity.Order;
import com.example.shop.order.Entity.OrderProduct;
import com.example.shop.product.DTO.ProductListDTO;

import java.util.List;

public interface OrderService {

    void createOrder(Long memberId, ProductListDTO productListDTO);

    OrderListDTO getAllOrders();

    OrderDetailDTO getOrder(Long orderId);

    void deleteOrder(Long orderId);


}
