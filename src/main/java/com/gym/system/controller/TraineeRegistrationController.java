package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TraineeRegistrationRequest;
import com.gym.system.dto.TraineeRegistrationResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/traineeRegistration")
public class TraineeRegistrationController {
    private final GymServices facade;

    public TraineeRegistrationController(GymServices facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<TraineeRegistrationResponse> register(
            @Valid @RequestBody TraineeRegistrationRequest request) {

        TraineeRegistrationResponse response = facade.createTrainee(request);

        return ResponseEntity.ok(response);
    }
}
