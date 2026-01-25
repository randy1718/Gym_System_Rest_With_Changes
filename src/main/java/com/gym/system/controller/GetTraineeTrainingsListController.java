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

import com.gym.system.dto.TraineeTrainingsListRequest;
import com.gym.system.dto.TraineeTrainingsListResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Get trainee's trainings list", description = "Endpoint to get all trainings of a trainee")
@RestController
@RequestMapping("/getTraineeTrainingsList")
public class GetTraineeTrainingsListController {
    private final GymServices facade;

    public GetTraineeTrainingsListController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Get trainee's trainings by providing their username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The trainee's trainings are retrieved correctly."),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @GetMapping
    public ResponseEntity<TraineeTrainingsListResponse> getTraineeTrainingsList(
            @Valid @RequestBody TraineeTrainingsListRequest request) {

        TraineeTrainingsListResponse response = facade.getTraineeTrainingsList(request);

        return ResponseEntity.ok(response);
    }
}
