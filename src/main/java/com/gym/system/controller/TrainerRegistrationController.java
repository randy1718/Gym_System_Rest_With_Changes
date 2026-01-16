package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TrainerRegistrationRequest;
import com.gym.system.dto.TrainerRegistrationResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/trainerRegistration")
public class TrainerRegistrationController {
    private final GymServices facade;

    public TrainerRegistrationController(GymServices facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<TrainerRegistrationResponse> register(
            @Valid @RequestBody TrainerRegistrationRequest request) {

        TrainerRegistrationResponse response = facade.createTrainer(request);

        return ResponseEntity.ok(response);
    }
}
