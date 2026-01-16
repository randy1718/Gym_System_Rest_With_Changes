package com.gym.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.DeleteTraineeRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/deleteTrainee")
public class DeleteTraineeController {
    private final GymServices facade;

    public DeleteTraineeController(GymServices facade) {
        this.facade = facade;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTrainee(
            @Valid @RequestBody DeleteTraineeRequest request) {

        Boolean authenticated = facade.deleteTrainee(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}    