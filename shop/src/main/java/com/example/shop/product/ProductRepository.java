package com.example.shop.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager em;

    public Product findById(long productId) {
        return em.find(Product.class, productId);
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    public Product findByName(String productName) {
        List<Product> productList = em.createQuery(
                "SELECT p FROM Product p WHERE p.productName = :productName", Product.class)
                .setParameter("productName", productName).getResultList();
        return productList.isEmpty() ? null : productList.get(0);
    }

    public void save(Product product) {
        em.persist(product);
    }

    public void deleteById(long productId) {
        Product product = em.find(Product.class, productId);
        em.remove(product);
    }
}
