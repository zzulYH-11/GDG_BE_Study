package com.example.shop.order;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
                "select o from Order o", Order.class)
                .getResultList();
    }


    public void saveOrder(Order order) {
        em.persist(order);
    }

    public void saveOrderProduct(OrderProduct orderProduct) {
        em.persist(orderProduct);
    }

    public List<OrderProduct> findAllProductsByOrderId(Long orderId) {
        return em.createQuery(
                "SELECT p FROM OrderProduct p WHERE p.order.id = :orderId", OrderProduct.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public void deleteById(long orderId) {
        Order order = em.find(Order.class, orderId);
        em.createQuery("DELETE FROM OrderProduct p WHERE p.order.id = :orderId")
                        .setParameter("orderId", orderId)
                .executeUpdate();
        em.remove(order);
    }
}
