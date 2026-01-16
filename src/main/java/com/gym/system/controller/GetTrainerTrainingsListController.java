package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TrainerTrainingsListRequest;
import com.gym.system.dto.TrainerTrainingsListResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/getTrainerTrainingsList")
public class GetTrainerTrainingsListController {
    private final GymServices facade;

    public GetTrainerTrainingsListController(GymServices facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<TrainerTrainingsListResponse> getTrainerTrainingsList(
            @Valid @RequestBody TrainerTrainingsListRequest request) {

        TrainerTrainingsListResponse response = facade.getTrainerTrainingsList(request);

        return ResponseEntity.ok(response);
    }
}
