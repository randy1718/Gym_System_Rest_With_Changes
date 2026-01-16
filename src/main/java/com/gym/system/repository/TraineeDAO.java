package com.gym.system.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gym.system.model.Trainee;
import com.gym.system.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TraineeDAO {

    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(TraineeDAO.class);

    @Autowired
    public TraineeDAO() {
    }

    public void save(Trainee trainee){
        logger.debug("Saving trainee in Database {}", trainee.getUsername());
        em.persist(trainee);
        em.flush();
    }

    public Trainee update(Trainee trainee){
        logger.debug("Updating trainee {}", trainee.getUsername());
        return em.merge(trainee);
    }

    public void delete(String username){
        logger.debug("Deleting trainee {}", username);
        em.createQuery(
        "SELECT t FROM Trainee t WHERE t.username = :username",
        Trainee.class
        )
        .setParameter("username", username)
        .getResultStream()
        .findFirst()
        .ifPresent(em::remove);
    }

    public void updateTrainersList(Trainer trainer, String username){
        Trainee trainee = em.createQuery(
            "SELECT t FROM Trainee t WHERE t.username = :username",
            Trainee.class
        ).setParameter("username", username)
        .getSingleResult();

        trainee.getTrainers().add(trainer);
    }

    public void updateTraineeTrainersList(String username, List<Trainer> trainers){
        Trainee trainee = em.createQuery(
            "SELECT t FROM Trainee t WHERE t.username = :username",
            Trainee.class
        ).setParameter("username", username)
        .getSingleResult();

        trainee.setTrainers(trainers);
    }

    public boolean toggleTraineeStatus(String username) {

        Trainee trainee = em.createQuery(
            "SELECT t FROM Trainee t WHERE t.username = :username",
            Trainee.class
        ).setParameter("username", username)
        .getSingleResult();

        trainee.setIsActive(!trainee.getIsActive());

        return trainee.getIsActive();
    }

    public Boolean activateDeactivateTrainee(String username, Boolean isActive) {

        Trainee trainee = em.createQuery(
            "SELECT t FROM Trainee t WHERE t.username = :username",
            Trainee.class
        ).setParameter("username", username)
        .getSingleResult();

        trainee.setIsActive(isActive);

        return true;
    }

    public Optional<Trainee> findByUsername(String username){
        logger.info("Finding trainee {}", username);
        return em.createQuery(
            "SELECT t FROM Trainee t WHERE LOWER(t.username) = LOWER(:username)",
            Trainee.class
        )
        .setParameter("username", username)
        .getResultStream()
        .findFirst();
    }

    public List<Trainee> findAll(){
        return em.createQuery(
                "SELECT t FROM Trainee t",
                Trainee.class
        ).getResultList();
    }
}
