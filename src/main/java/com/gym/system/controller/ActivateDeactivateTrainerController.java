package com.gym.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.ActivateDeactivateTrainerRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/activateDeactivateTrainer")
public class ActivateDeactivateTrainerController {
    private final GymServices facade;

    public ActivateDeactivateTrainerController(GymServices facade) {
        this.facade = facade;
    }

    @PatchMapping
    public ResponseEntity<Void> activateDeactivateTrainer(
            @Valid @RequestBody ActivateDeactivateTrainerRequest request) {

        Boolean authenticated = facade.activateDeactivateTrainer(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}    