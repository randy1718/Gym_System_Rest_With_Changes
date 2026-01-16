package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.UpdateTrainerRequest;
import com.gym.system.dto.UpdateTrainerResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/updateTrainer")
public class UpdateTrainerController {
    private final GymServices facade;

    public UpdateTrainerController(GymServices facade) {
        this.facade = facade;
    }

    @PutMapping
    public ResponseEntity<UpdateTrainerResponse> updateTrainer(
            @Valid @RequestBody UpdateTrainerRequest request) {

        UpdateTrainerResponse response = facade.updateTrainer(request);

        return ResponseEntity.ok(response);
    }
}
