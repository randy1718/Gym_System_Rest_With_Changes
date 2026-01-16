package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.UnassignedTrainersRequest;
import com.gym.system.dto.UnassignedTrainersResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/getUnassignedTrainers")
public class GetUnassignedTrainersController {
    private final GymServices facade;

    public GetUnassignedTrainersController(GymServices facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<UnassignedTrainersResponse> getUnassignedTrainers(
            @Valid @RequestBody UnassignedTrainersRequest request) {

        UnassignedTrainersResponse response = facade.getUnassignedTrainers(request);

        return ResponseEntity.ok(response);
    }
}
