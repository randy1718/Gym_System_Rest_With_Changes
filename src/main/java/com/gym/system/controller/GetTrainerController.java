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

import com.gym.system.dto.TrainerProfileRequest;
import com.gym.system.dto.TrainerProfileResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Get trainer's information", description = "Endpoint to get the information of a trainer")
@RestController
@RequestMapping("/getTrainer")
public class GetTrainerController {
    private final GymServices facade;

    public GetTrainerController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Get the information of a trainer by providing their username and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The information of the trainer is retrieved correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @GetMapping
    public ResponseEntity<TrainerProfileResponse> getTrainer(
            @Valid @RequestBody TrainerProfileRequest request) {

        TrainerProfileResponse response = facade.getTrainer(request);

        return ResponseEntity.ok(response);
    }
}
