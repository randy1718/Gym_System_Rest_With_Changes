package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TraineeProfileRequest;
import com.gym.system.dto.TraineeProfileResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/getTrainee")
public class GetTraineeController {
    private final GymServices facade;

    public GetTraineeController(GymServices facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<TraineeProfileResponse> getTrainee(
            @Valid @RequestBody TraineeProfileRequest request) {

        TraineeProfileResponse response = facade.getTrainee(request);

        return ResponseEntity.ok(response);
    }
}
