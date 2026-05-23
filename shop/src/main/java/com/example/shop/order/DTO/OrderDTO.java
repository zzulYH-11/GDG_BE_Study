package com.example.shop.order.DTO;

import com.example.shop.order.Entity.Order;
import java.time.LocalDateTime;

public record OrderDTO (
        Long orderId,
        Long memberId,
        LocalDateTime dateTime
)
{
    public static OrderDTO from(Order order) {
        return new OrderDTO(order.getOrderId(),
                order.getMember().getId(),
                order.getOrderDateTime());
    }
}
