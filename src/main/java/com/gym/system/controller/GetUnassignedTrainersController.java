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

import com.gym.system.dto.UnassignedTrainersRequest;
import com.gym.system.dto.UnassignedTrainersResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Get unassigned trainers", description = "Endpoint to get all unassigned trainers")
@RestController
@RequestMapping("/getUnassignedTrainers")
public class GetUnassignedTrainersController {
    private final GymServices facade;

    public GetUnassignedTrainersController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Get all unassigned trainers who don´t have trainings with the specific trainee")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Unassigned trainers are retrieved correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @GetMapping
    public ResponseEntity<UnassignedTrainersResponse> getUnassignedTrainers(
            @Valid @RequestBody UnassignedTrainersRequest request) {

        UnassignedTrainersResponse response = facade.getUnassignedTrainers(request);

        return ResponseEntity.ok(response);
    }
}
