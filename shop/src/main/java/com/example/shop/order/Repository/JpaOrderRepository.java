package com.example.shop.order.Repository;


import com.example.shop.order.Entity.Order;
import com.example.shop.order.Entity.OrderProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JpaOrderRepository implements OrderRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Order findById(long orderId) {
        return em.find(Order.class, orderId);
    }

    @Override
    public List<Order> findAll() {
        return em.createQuery(
                "select o from Order o", Order.class)
                .getResultList();
    }

    @Override
    public void saveOrder(Order order) {
        em.persist(order);
    }

    @Override
    public void saveOrderProduct(OrderProduct orderProduct) {
        em.persist(orderProduct);
    }

    @Override
    public List<OrderProduct> findAllProductsByOrderId(Long orderId) {
        return em.createQuery(
                "SELECT p FROM OrderProduct p WHERE p.order.id = :orderId", OrderProduct.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    @Override
    public void deleteById(Long orderId) {
        Order order = em.find(Order.class, orderId);
        em.createQuery("DELETE FROM OrderProduct p WHERE p.order.id = :orderId")
                        .setParameter("orderId", orderId)
                .executeUpdate();
        em.remove(order);
    }
}
