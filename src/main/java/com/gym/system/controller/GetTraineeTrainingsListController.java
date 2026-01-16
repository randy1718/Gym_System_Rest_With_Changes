package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TraineeTrainingsListRequest;
import com.gym.system.dto.TraineeTrainingsListResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/getTraineeTrainingsList")
public class GetTraineeTrainingsListController {
    private final GymServices facade;

    public GetTraineeTrainingsListController(GymServices facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<TraineeTrainingsListResponse> getTraineeTrainingsList(
            @Valid @RequestBody TraineeTrainingsListRequest request) {

        TraineeTrainingsListResponse response = facade.getTraineeTrainingsList(request);

        return ResponseEntity.ok(response);
    }
}
