package com.example.shop.order.Service;

import com.example.shop.common.exception.BadRequestException;
import com.example.shop.common.exception.NotFoundException;
import com.example.shop.common.message.ErrorMessage;
import com.example.shop.member.Entity.Member;
import com.example.shop.member.Repository.MemberRepository;
import com.example.shop.order.DTO.OrderDTO;
import com.example.shop.order.DTO.OrderDetailDTO;
import com.example.shop.order.DTO.OrderListDTO;
import com.example.shop.order.Entity.Order;
import com.example.shop.order.Entity.OrderProduct;
import com.example.shop.order.Repository.JpaOrderRepository;
import com.example.shop.product.DTO.ProductDTO;
import com.example.shop.product.DTO.ProductListDTO;
import com.example.shop.product.Entity.Product;
import com.example.shop.product.Repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final JpaOrderRepository orderRepository;
    private final JpaProductRepository productRepository;

    @Override
    @Transactional
    public void createOrder(Long memberId, ProductListDTO productListDTO) {

        //받아온 멤버 아이디로 멤버 찾고 시간 정보 생성하기
        Member member = memberRepository.findById(memberId);

        LocalDateTime orderDateTime = LocalDateTime.now();

        //주문을 생성하고 저장
        Order order = new Order(member, orderDateTime);
        orderRepository.saveOrder(order);

        //for문을 돌며 각 상품이 품절이거거나 수량이 부족한지 확인하고 주문 정보에 저장한다
        for(ProductDTO productDTO : productListDTO.products()) {

            // 이름으로 상품 찾아오기
            Product product = productRepository.findByName(productDTO.name());

            if(product == null) {
                throw new BadRequestException(ErrorMessage.PRODUCT_NOT_EXIST);
            }
            if(product.getQuantity() < productDTO.quantity()) {
                throw new BadRequestException(ErrorMessage.PRODUCT_OUT_OF_STOCK);
            }

            // 상품이 존재하면 주문정보 저장하기
            OrderProduct orderProduct =
                    new OrderProduct(order, product, productDTO.quantity(), productDTO.price());
            orderRepository.saveOrderProduct(orderProduct);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrderListDTO getAllOrders() {

        List<OrderDTO> orderDTOList = orderRepository.findAll().stream()
                .map(OrderDTO::from).toList();

        return new OrderListDTO(orderDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailDTO getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        // 존재하는 주문인지 확인
        if(order == null){
            throw new NotFoundException(ErrorMessage.ORDER_NOT_EXIST +  orderId);
        }

        OrderDTO orderDTO = OrderDTO.from(order);

        List<ProductDTO> productDTOs = new ArrayList<>();
        List<OrderProduct> allProductsByOrderId = orderRepository.findAllProductsByOrderId(orderId);

        for(OrderProduct orderProduct : allProductsByOrderId){
            productDTOs.add(ProductDTO.from(orderProduct.getProduct()));
        }


        return new OrderDetailDTO(orderDTO, new ProductListDTO(productDTOs));
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);

        // 존재하는 주문인지 확인
        if(order == null){
            throw new NotFoundException(ErrorMessage.ORDER_NOT_EXIST +  orderId);
        }

        //존재하면 삭제
        orderRepository.deleteById(orderId);
    }

}
