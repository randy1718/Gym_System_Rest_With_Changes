package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TrainingTypesResponse;
import com.gym.system.service.GymServices;


@RestController
@RequestMapping("/trainingTypes")
public class GetTrainingTypesController {
    private final GymServices facade;

    public GetTrainingTypesController(GymServices facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<TrainingTypesResponse> getTrainingTypes() {

        TrainingTypesResponse response = facade.getTrainingTypes();

        return ResponseEntity.ok(response);
    }
}    