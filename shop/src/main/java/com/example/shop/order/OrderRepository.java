package com.example.shop.order;

import com.example.shop.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public Order findById(long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll() {
        return em.createQuery(
                "SELECT o FROM Order o", Order.class)
                .getResultList();
    }

    public void save(Order order) {
        em.persist(order);
    }

    public void deleteById(long orderId) {
        Order order = em.find(Order.class, orderId);
        em.remove(order);
    }
}
