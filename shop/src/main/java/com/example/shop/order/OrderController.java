package com.example.shop.order;

import com.example.shop.order.dto.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    //주문 정보 생성 -> Post , /orders
    @PostMapping("/{memberId}")
    public ResponseEntity<Void> createOrder(@PathVariable Long memberId, @RequestBody OrderCreateRequest orderCreateRequest) {

        orderService.createOrder(memberId, orderCreateRequest);

        return ResponseEntity.ok().build();
    }

    // 전체 주문 조회 -> Get , /orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // 개별 주문 정보 상세 조회 -> Get , /orders/{ordersID}
    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderProduct>> getOrder(@PathVariable Long orderId) {
        List<OrderProduct> allProducts = orderService.getOrder(orderId);
        return ResponseEntity.ok(allProducts);
    }

    //주문 취소 -> Delete , /orders/{orderId}
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
