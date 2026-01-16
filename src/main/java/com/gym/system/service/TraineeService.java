package com.gym.system.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gym.system.model.Trainee;
import com.gym.system.model.Trainer;
import com.gym.system.repository.TraineeDAO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TraineeService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);
    private final TraineeDAO traineeDAO;

    @Autowired
    public  TraineeService(TraineeDAO traineeDAO) { 
        this.traineeDAO = traineeDAO;
    }

    public boolean authenticate(String username, String password) {

        Trainee trainee = traineeDAO.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("Invalid credentials - trainee"));

        if (!trainee.getIsActive()) {
            throw new IllegalStateException("Account is deactivated");
        }

        if (!trainee.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials - trainee");
        }
        
        return traineeDAO.findByUsername(username)
            .map(t -> t.getPassword().equals(password))
            .orElse(false);
    }

    public void changePassword(String username, String oldPassword, String newPassword) {

        Trainee trainee = traineeDAO.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Boolean isAuthenticated = authenticate(username, oldPassword);

        if(isAuthenticated){
            trainee.setPassword(newPassword);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void toggleStatus(String username, String password) {
        Trainee trainee = traineeDAO.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Boolean isAuthenticated = authenticate(username, password);
        
        if(isAuthenticated){
            traineeDAO.toggleTraineeStatus(username);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void create(Trainee t){
        logger.info("Service: Creating trainee {} {}", t.getFirstName(), t.getLastName());
        traineeDAO.save(t);
    }

    public void update(String username, String password, Trainee t){
        logger.info("Service: Updating trainee with id {}", t.getId());
        
        Boolean isAuthenticated = authenticate(username, password);

        if(isAuthenticated){
            traineeDAO.update(t);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void updateTrainersList(Trainer trainer, String traineeUsername, String traineePassword){
        logger.info("Service: Updating trainers list for trainee {}", traineeUsername);

        Boolean isAuthenticated = authenticate(traineeUsername, traineePassword);

        if(isAuthenticated){
            traineeDAO.updateTrainersList(trainer, traineeUsername, traineePassword);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void delete(String username, String password){
        logger.info("Service: Deleting trainee with username {}", username);
        Trainee trainee = traineeDAO.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Boolean isAuthenticated = authenticate(username, password);
        
        if(isAuthenticated){
            traineeDAO.delete(username);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public boolean toggleTraineeStatus(String username, String password) {

        Boolean isAuthenticated = authenticate(username, password);
        
        if(isAuthenticated){
            return traineeDAO.toggleTraineeStatus(username);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public Optional<Trainee> findByUsername(String username, String password){
        logger.info("Service: Finding trainee with username {}", username);

        Boolean isAuthenticated = authenticate(username, password);
        
        if(isAuthenticated){
            return traineeDAO.findByUsername(username);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public List<Trainee> findAll(){
        logger.info("Service: Retrieving all trainees");
        return traineeDAO.findAll();
    }
}
