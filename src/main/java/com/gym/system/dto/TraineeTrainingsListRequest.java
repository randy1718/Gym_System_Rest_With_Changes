package com.gym.system.dto;

import jakarta.validation.constraints.NotBlank;

public class TraineeTrainingsListRequest {
    @NotBlank
    private String username;

    private String from;

    private String to;

    private String trainerName;

    private String trainingType;
    
    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getFrom(){
        return from;
    }

    public void setFrom(String from){
        this.from = from;
    }

    public String getTo(){
        return to;
    }

    public void setTo(String to){
        this.to = to;
    }

    public String getTrainerName(){
        return trainerName;
    }

    public void setTrainerName(String trainerName){
        this.trainerName = trainerName;
    }

    public String getTrainingType(){
        return trainingType;
    }

    public void setTrainingType(String trainingType){
        this.trainingType = trainingType;
    }
}
