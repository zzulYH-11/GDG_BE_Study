package com.example.shop.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    //@Transactional
    public Long createOrder(OrderCreateRequest request) {

        // 주문한 상품이 품절됐는지 확인
        Product product = productRepository.findByName(request.getOrderProductName());
        if(product.getStock() == 0){
            throw new RuntimeException ("품절된 상품입니다." + request.getOrderProductName());
        }
        // 주문한 상품의 재고가 충분한지 확인
        if(product.getStock() < request.getOrderQuantity()){
            throw new RuntimeException ("재고가 부족합니다. 남은 재고량 : " + request.getOrderQuantity());
        }
        // 주문 가능한 상품이면 주문 정보 저장
        Order order = new Order(request.getOrderProductName(), request.getOrderQuantity(), request.getOrderDate());
        orderRepository.save(order);
        return order.getId();
    }

    //@Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //@Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);

        // 존재하는 주문인지 확인
        if(order == null){
            throw new RuntimeException("존재하지 않는 주문입니다. " +  orderId);
        }

        return order;
    }

    //@Transactional
    public void deleteOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        // 존재하는 주문인지 확인
        if(order == null){
            throw new RuntimeException("존재하지 않는 주문입니다. " +  ordersId);
        }

        //존재하면 삭제
        orderRepository.deleteById(orderId);

    }

}
