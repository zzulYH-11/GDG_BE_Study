package com.example.shop.member.Repository;

import com.example.shop.member.Entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaMemberRepository implements MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery(
                        "SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    @Override
    public Member findByLoginId(String loginId) {
        List<Member> result = em.createQuery(
                "SELECT m FROM Member m WHERE m.loginId = :loginId", Member.class
        ).setParameter("loginId", loginId).getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public void deleteById(Long id) {
        Member member = em.find(Member.class, id);
        em.remove(member);
    }
}
