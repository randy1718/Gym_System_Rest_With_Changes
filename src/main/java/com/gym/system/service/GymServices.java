package com.gym.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gym.system.model.*;

@Component
public class GymServices {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;

    public GymServices(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService, TrainingTypeService trainingTypeService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.trainingTypeService = trainingTypeService;
    }

    public void createTrainee(Trainee t){
        traineeService.create(t);
    }

    public void createTrainer(Trainer t){
        trainerService.create(t);
    }

    public void createTrainingType(TrainingType t){
        trainingTypeService.create(t);
    }

    public void createTraining(Training t, String username, String password){
        trainingService.create(t, username, password);
    }

    public void updateTrainee(String username, String password, Trainee t){
        traineeService.update(username, password, t);
    }

    public void updateTrainer(String username, String password, Trainer t){
        trainerService.update(username, password, t);
    }

    public void deleteTrainee(String username, String password){
        traineeService.delete(username, password);
    }

    public void toggleTraineeStatus(String username, String password){
        traineeService.toggleStatus(username, password);
    }

    public void toggleTrainerStatus(String username, String password){
        trainerService.toggleStatus(username, password);
    }

    public void changeTraineePassword(String username, String oldPassword, String newPassword){
        traineeService.changePassword(username, oldPassword, newPassword);
    }

    public void changeTrainerPassword(String username, String oldPassword, String newPassword){
        trainerService.changePassword(username, oldPassword, newPassword);
    }

    public Optional<Trainee> findTraineeByUsername(String username, String password){
        return traineeService.findByUsername(username, password);
    }

    public Optional<Trainer> findTrainerByUsername(String username, String password){
        return trainerService.findByUsername(username, password);
    }

    public Optional<TrainingType> findTrainingTypeByName(String name){
        return trainingTypeService.findByName(name);
    }

    public Optional<Training> findTrainingByTraineeUsernameAndDate(String username, String password, String date){
        return trainingService.findByTraineeUsernameAndDate(username, password, date);
    }

    public Optional<Training> findTrainingById(String id){
        return trainingService.findById(id);
    }

    public List<Trainee> findAllTrainees(){
        return traineeService.findAll();
    }

    public List<Trainer> findAllTrainers(){
        return trainerService.findAll();
    }

    public List<Training> findAllTrainings(){
        return trainingService.findAll();
    }

    public List<TrainingType> findAllTrainingTypes(){
        return trainingTypeService.findAll();
    }

    public List<Trainer> findUnassignedTrainers(String username, String password){
        return trainingService.findUnassignedTrainers(username, password);
    }

    public List<Training> findTrainingsByTraineeUsername(String username, String password, String fromDate, String toDate, String trainerName, String trainingType){
        return trainingService.findTrainingsByTraineeUsername(username, password, fromDate, toDate, trainerName, trainingType);
    }

    public List<Training> findTrainingsByTrainerUsername(String username, String password, String fromDate, String toDate, String traineeName, String trainingType){
        return trainingService.findTrainingsByTrainerUsername(username, password, fromDate, toDate, traineeName, trainingType);
    }

    public Boolean authenticateTrainee(String username, String password){
        return traineeService.authenticate(username, password);
    }

    public Boolean authenticateTrainer(String username, String password){
        return trainerService.authenticate(username, password);
    }

    public void updateTraineeTrainersList(Trainer trainer, String traineeUsername, String traineePassword){
        traineeService.updateTrainersList(trainer, traineeUsername, traineePassword);
    }

}
