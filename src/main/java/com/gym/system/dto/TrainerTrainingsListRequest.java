package com.gym.system.dto;

import jakarta.validation.constraints.NotBlank;

public class TrainerTrainingsListRequest {
    @NotBlank
    private String username;

    private String from;

    private String to;

    private String traineeName;
    
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

    public String getTraineeName(){
        return traineeName;
    }

    public void setTraineeName(String traineeName){
        this.traineeName = traineeName;
    }
}
