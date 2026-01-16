package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TrainerProfileRequest;
import com.gym.system.dto.TrainerProfileResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/getTrainer")
public class GetTrainerController {
    private final GymServices facade;

    public GetTrainerController(GymServices facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<TrainerProfileResponse> getTrainer(
            @Valid @RequestBody TrainerProfileRequest request) {

        TrainerProfileResponse response = facade.getTrainer(request);

        return ResponseEntity.ok(response);
    }
}
