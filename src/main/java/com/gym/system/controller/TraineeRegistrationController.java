package com.gym.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.TraineeRegistrationRequest;
import com.gym.system.dto.TraineeRegistrationResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Register a new trainee", description = "Endpoint to register/create a new trainee")
@RestController
@RequestMapping("/traineeRegistration")
public class TraineeRegistrationController {
    private final GymServices facade;

    public TraineeRegistrationController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Register a new Trainee by providing their information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The trainee is created correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PostMapping
    public ResponseEntity<TraineeRegistrationResponse> register(
            @Valid @RequestBody TraineeRegistrationRequest request) {

        TraineeRegistrationResponse response = facade.createTrainee(request);

        return ResponseEntity.ok(response);
    }
}
