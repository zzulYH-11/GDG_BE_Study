package com.example.shop.product.Repository;

import com.example.shop.product.Entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaProductRepository implements ProductRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product findById(long productId) {
        return em.find(Product.class, productId);
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    @Override
    public Product findByName(String name) {
        List<Product> productList = em.createQuery(
                "SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", name).getResultList();
        return productList.isEmpty() ? null : productList.get(0);
    }

    @Override
    public void save(Product product) {
        em.persist(product);
    }

    @Override
    public void deleteById(long productId) {
        Product product = em.find(Product.class, productId);
        em.remove(product);
    }
}
