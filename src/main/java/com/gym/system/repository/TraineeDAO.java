package com.gym.system.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gym.system.model.Trainee;
import com.gym.system.model.Trainer;
import com.gym.system.util.PasswordGenerator;
import com.gym.system.util.UsernameDuplicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TraineeDAO {

    @PersistenceContext
    private EntityManager em;

    private final UsernameDuplicates usernameDuplicates;

    private static final Logger logger = LoggerFactory.getLogger(TraineeDAO.class);

    @Autowired
    public TraineeDAO(UsernameDuplicates usernameDuplicates) {
        this.usernameDuplicates = usernameDuplicates;
    }

    public void save(Trainee trainee){
        String username = trainee.getFirstName() + "." + trainee.getLastName();
        trainee.setUsername(usernameDuplicates.generateUniqueUsername(username));
        trainee.setPassword(PasswordGenerator.generate());
        trainee.setIsActive(true);
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

    public void updateTrainersList(Trainer trainer, String username, String password){
        Trainee trainee = em.createQuery(
            "SELECT t FROM Trainee t WHERE t.username = :username",
            Trainee.class
        ).setParameter("username", username)
        .getSingleResult();

        trainee.getTrainers().add(trainer);
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

    public Optional<Trainee> findByUsername(String username){
        logger.debug("Finding trainee {}", username);
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
