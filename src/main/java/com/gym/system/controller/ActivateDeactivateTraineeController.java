package com.gym.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.ActivateDeactivateTraineeRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/activateDeactivateTrainee")
public class ActivateDeactivateTraineeController {
    private final GymServices facade;

    public ActivateDeactivateTraineeController(GymServices facade) {
        this.facade = facade;
    }

    @PatchMapping
    public ResponseEntity<Void> activateDeactivateTrainee(
            @Valid @RequestBody ActivateDeactivateTraineeRequest request) {

        Boolean authenticated = facade.activateDeactivateTrainee(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}    