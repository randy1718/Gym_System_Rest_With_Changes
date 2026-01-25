package com.gym.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TrainerTrainingsListRequest;
import com.gym.system.dto.TrainerTrainingsListResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Get trainer's trainings list", description = "Endpoint to get all trainings of a trainer")
@RestController
@RequestMapping("/getTrainerTrainingsList")
public class GetTrainerTrainingsListController {
    private final GymServices facade;

    public GetTrainerTrainingsListController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Get trainer's trainings by providing their username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The trainer's trainings are retrieved correctly."),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @GetMapping
    public ResponseEntity<TrainerTrainingsListResponse> getTrainerTrainingsList(
            @Valid @RequestBody TrainerTrainingsListRequest request) {

        TrainerTrainingsListResponse response = facade.getTrainerTrainingsList(request);

        return ResponseEntity.ok(response);
    }
}
