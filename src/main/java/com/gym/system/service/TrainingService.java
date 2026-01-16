package com.gym.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gym.system.model.Trainee;
import com.gym.system.model.Trainer;
import com.gym.system.model.Training;
import com.gym.system.model.TrainingType;
import com.gym.system.repository.TraineeDAO;
import com.gym.system.repository.TrainerDAO;
import com.gym.system.repository.TrainingDAO;
import com.gym.system.repository.TrainingTypeDAO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TrainingService {

    private final TrainingDAO trainingDAO;
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;
    private final TrainingTypeDAO trainingTypeDAO;
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final AuthService AuthService;

    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    public TrainingService(TrainingDAO trainingDAO, TrainerDAO trainerDAO, TraineeDAO traineeDAO, TrainingTypeDAO trainingTypeDAO, TrainerService trainerService, TraineeService traineeService, AuthService AuthService) { 
        this.trainingDAO = trainingDAO;
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
        this.trainingTypeDAO = trainingTypeDAO;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.AuthService = AuthService;
    }



    public void create(Training t, String username, String password){

        Boolean isAuthenticated = AuthService.authenticate(username, password);

        if(isAuthenticated){
            logger.info("Service: Creating new training session for trainee {} with trainer {}",
                    t.getTrainee().getId(), t.getTrainer().getId());

            logger.debug("Service: Validating trainer with Username {}", t.getTrainer().getUsername());
            Optional<Trainer> foundTrainer = trainerDAO.findByUsername(t.getTrainer().getUsername());
            if (!foundTrainer.isPresent()) {
                logger.error("Service: Trainer {} does not exist", t.getTrainer().getUsername());
            }

            logger.debug("Service: Validating trainee with Username {}", t.getTrainee().getUsername());
            Optional<Trainee> foundTrainee = traineeDAO.findByUsername(t.getTrainee().getUsername());
            if (!foundTrainee.isPresent()) {
                logger.error("Service: Trainee {} does not exist", t.getTrainee().getUsername());
                throw new IllegalArgumentException("Trainee Username does not exist");
            }

            logger.debug("Service: Validating training type {}", t.getTrainingType().getId());
            Optional<TrainingType> foundTrainingType = trainingTypeDAO.findByName(t.getTrainingType().getName());
            if (!foundTrainingType.isPresent()) {
                logger.error("Service: Training type {} does not exist", t.getTrainingType().getId());
                throw new IllegalArgumentException("Training type does not exist");
            }

            logger.info("Service: All validations passed. Saving training...");
            trainingDAO.save(t);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public Optional<Training> findById(String id){
        logger.info("Service: Fetching training with id {}", id);
        return trainingDAO.findById(id);
    }

    public List<Training> findTrainingsByTraineeUsername(String username, String password, String fromDate, String toDate, String trainerName, String trainingType){
        logger.info("Service: Fetching trainings for trainee {}", username);

        Boolean isAuthenticated = traineeService.authenticate(username, password);

        if(isAuthenticated){
            return trainingDAO.findTrainingsByTraineeUsername(username, fromDate, toDate, trainerName, trainingType);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public List<Training> findTrainingsByTrainerUsername(String username, String password, String fromDate, String toDate, String traineeName, String trainingType){
        logger.info("Service: Fetching trainings for trainer {}", username);

        Boolean isAuthenticated = trainerService.authenticate(username, password);

        if(isAuthenticated){
            return trainingDAO.findTrainingsByTrainerUsername(username, fromDate, toDate, traineeName, trainingType);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public List<Trainer> findUnassignedTrainers(String username, String password){
        logger.info("Service: Retrieving unassigned trainers");
        Boolean isAuthenticated = traineeService.authenticate(username, password);

        if(isAuthenticated){
            return trainingDAO.findUnassignedTrainers(username);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public Optional<Training> findByTraineeUsernameAndDate(String username, String password, String date){
        logger.info("Service: Fetching training for trainee {} on date {}", username, date);
        Boolean isAuthenticated = AuthService.authenticate(username, password);

        if(isAuthenticated){
            return trainingDAO.findByTraineeUsernameAndDate(username, date);
        }else{
            throw new IllegalArgumentException("Invalid credentials");
        }
        
    }

    public List<Training> findAll(){
        logger.info("Service: Fetching all trainings");
        return trainingDAO.findAll();
    }
}
