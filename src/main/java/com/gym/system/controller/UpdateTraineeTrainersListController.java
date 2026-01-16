package com.gym.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.UpdateTrainersListRequest;
import com.gym.system.dto.UpdateTrainersListResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/updateTraineeTrainersList")
public class UpdateTraineeTrainersListController {
    private final GymServices facade;

    public UpdateTraineeTrainersListController(GymServices facade) {
        this.facade = facade;
    }

    @PutMapping
    public ResponseEntity<UpdateTrainersListResponse> UpdateTraineeTrainersList(
            @Valid @RequestBody UpdateTrainersListRequest request) {

        UpdateTrainersListResponse response = facade.UpdateTraineeTrainersList(request);

        return ResponseEntity.ok(response);
    }
}
