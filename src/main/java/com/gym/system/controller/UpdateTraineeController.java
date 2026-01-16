package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.UpdateTraineeRequest;
import com.gym.system.dto.UpdateTraineeResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/updateTrainee")
public class UpdateTraineeController {
    private final GymServices facade;

    public UpdateTraineeController(GymServices facade) {
        this.facade = facade;
    }

    @PutMapping
    public ResponseEntity<UpdateTraineeResponse> updateTrainee(
            @Valid @RequestBody UpdateTraineeRequest request) {

        UpdateTraineeResponse response = facade.updateTrainee(request);

        return ResponseEntity.ok(response);
    }
}
