package com.gym.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.AddTrainingRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/addTraining")
public class AddTrainingController {
    private final GymServices facade;

    public AddTrainingController(GymServices facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<Void> addTraining(
            @Valid @RequestBody AddTrainingRequest request) {

        Boolean authenticated = facade.addTraining(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}    