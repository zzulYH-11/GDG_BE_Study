package com.example.shop.order.Service;

import com.example.shop.member.Entity.Member;
import com.example.shop.member.Repository.MemberRepository;
import com.example.shop.order.DTO.OrderCreateRequest;
import com.example.shop.order.Entity.Order;
import com.example.shop.order.Entity.OrderProduct;
import com.example.shop.order.Repository.JpaOrderRepository;
import com.example.shop.product.DTO.Product;
import com.example.shop.product.Repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final JpaOrderRepository orderRepository;
    private final JpaProductRepository productRepository;

    @Override
    @Transactional
    public void createOrder(Long memberId, OrderCreateRequest orderCreateRequest) {

        //받아온 멤버 아이디로 멤버 찾고 시간 정보 생성하기
        Member member = memberRepository.findById(memberId);
        LocalDateTime orderDateTime = LocalDateTime.now();

        //주문을 생성하고 저장
        Order order = new Order(member, orderDateTime);
        orderRepository.saveOrder(order);

        //for문을 돌며 각 상품이 품절이거거나 수량이 부족한지 확인하고 주문 정보에 저장한다
        for(OrderCreateRequest.ProductDto productDto : orderCreateRequest.getProducts()) {

            // 이름으로 상품 찾아오기
            Product product = productRepository.findByName(productDto.getName());

            if(product == null) {
                throw new RuntimeException("존재하지 않는 상품입니다.");
            }
            if(product.getQuantity() < productDto.getQuantity()) {
                throw new RuntimeException("상품의 재고가 부족합니다.");
            }

            // 상품이 존재하면 주문정보 저장하기
            OrderProduct orderProduct =
                    new OrderProduct(order, product, productDto.getQuantity(), productDto.getPrice());
            orderRepository.saveOrderProduct(orderProduct);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderProduct> getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        // 존재하는 주문인지 확인
        if(order == null){
            throw new RuntimeException("존재하지 않는 주문입니다. " +  orderId);
        }

        List<OrderProduct> allProducts = orderRepository.findAllProductsByOrderId(orderId);

        return allProducts;
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        // 존재하는 주문인지 확인
        if(order == null){
            throw new RuntimeException("존재하지 않는 주문입니다. " +  orderId);
        }

        //존재하면 삭제
        orderRepository.deleteById(orderId);
    }

}
