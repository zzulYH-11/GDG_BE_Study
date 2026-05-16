package com.example.shop.order.Entity;

import com.example.shop.member.Entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDateTime;

    public Order(Member member, LocalDateTime orderDateTime) {
        this.member = member;
        this.orderDateTime = orderDateTime;
    }

}
