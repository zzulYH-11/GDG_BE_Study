package com.example.shop.order.DTO;

import java.util.List;

public record OrderListDTO (
        List<OrderDTO> orderDTOList
){
}
