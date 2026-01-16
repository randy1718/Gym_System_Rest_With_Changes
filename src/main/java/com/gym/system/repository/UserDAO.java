package com.gym.system.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public boolean existsByUsernameIgnoreCase(String username) {
        Long count = em.createQuery("""
            SELECT COUNT(u) FROM User u
            WHERE LOWER(u.username) = LOWER(:username)
        """, Long.class)
        .setParameter("username", username)
        .getSingleResult();

        return count > 0;
    }
}
