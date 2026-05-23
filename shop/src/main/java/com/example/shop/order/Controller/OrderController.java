package com.example.shop.order.Controller;

import com.example.shop.order.DTO.OrderDetailDTO;
import com.example.shop.order.DTO.OrderListDTO;
import com.example.shop.order.Service.OrderServiceImpl;
import com.example.shop.product.DTO.ProductListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "주문 컨트롤러", description = "주문 CRUD 수행")
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/{memberId}")
    @Operation(summary = "주문 정보 생성", description = "상품 이름, 가격, 수량을 받아 주문을 생성한다.")
    public ResponseEntity<Void> createOrder(@PathVariable Long memberId, @Valid @RequestBody ProductListDTO productListDTO) {

        orderService.createOrder(memberId, productListDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "전체 주문 조회", description = "모든 주문들을 조회한다.")
    public ResponseEntity<OrderListDTO> getAllOrders() {
        OrderListDTO allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "개별 주문 정보 상세 조회", description = "주문 Id를 통해 주문 정보를 조회한다")
    public ResponseEntity<OrderDetailDTO> getOrder(@PathVariable Long orderId) {
        OrderDetailDTO order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 취소", description = "Id를 통해 주문을 취소한다")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
