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

import com.gym.system.dto.TrainerRegistrationRequest;
import com.gym.system.dto.TrainerRegistrationResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Register a new trainer", description = "Endpoint to register/create a new trainer")
@RestController
@RequestMapping("/trainerRegistration")
public class TrainerRegistrationController {
    private final GymServices facade;

    public TrainerRegistrationController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Register a new Trainer by providing their information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The trainer is created correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PostMapping
    public ResponseEntity<TrainerRegistrationResponse> register(
            @Valid @RequestBody TrainerRegistrationRequest request) {

        TrainerRegistrationResponse response = facade.createTrainer(request);

        return ResponseEntity.ok(response);
    }
}
