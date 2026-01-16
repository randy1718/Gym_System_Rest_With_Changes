package com.gym.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gym.system.model.Trainer;
import com.gym.system.repository.TrainerDAO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TrainerService {

    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO) { 
        this.trainerDAO = trainerDAO;
    }

    public boolean authenticate(String username, String password) {

        Trainer trainer = trainerDAO.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("Invalid credentials - trainer"));

        if (!trainer.getIsActive()) {
            throw new IllegalStateException("Account is deactivated");
        }

        if (!trainer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials - trainer");
        }
        
        return trainerDAO.findByUsername(username)
            .map(t -> t.getPassword().equals(password))
            .orElse(false);
    }

    public void changePassword(String username, String oldPassword, String newPassword) {

        Trainer trainer = trainerDAO.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Boolean isAuthenticated = authenticate(username, oldPassword);

        if(isAuthenticated){
            trainer.setPassword(newPassword);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void toggleStatus(String username, String password) {
        Trainer trainer = trainerDAO.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Boolean isAuthenticated = authenticate(username, password);

        if(isAuthenticated){
            trainerDAO.toggleTrainerStatus(username);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }   
    }

    public void create(Trainer t){
        logger.info("Service: Creating trainer {} {}", t.getFirstName(), t.getLastName());
        trainerDAO.save(t);
    }

    public void update(String username, String password, Trainer t){
        logger.info("Service: Updating trainer with id {}", t.getId());

        Boolean isAuthenticated = authenticate(username, password);

        if(isAuthenticated){
            trainerDAO.update(t);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public Optional<Trainer> findByUsername(String username, String password){
        logger.info("Service: Finding trainer with username {}", username);

        Boolean isAuthenticated = authenticate(username, password);

        if(isAuthenticated){
            return trainerDAO.findByUsername(username);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }  
    }

    public List<Trainer> findAll(){
        logger.info("Service: Retrieving all trainers");
        return trainerDAO.findAll();
    }
}
