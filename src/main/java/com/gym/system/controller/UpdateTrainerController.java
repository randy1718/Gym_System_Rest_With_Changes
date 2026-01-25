package com.gym.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.UpdateTrainerRequest;
import com.gym.system.dto.UpdateTrainerResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Update a trainer", description = "Endpoint to update a trainer")
@RestController
@RequestMapping("/updateTrainer")
public class UpdateTrainerController {
    private final GymServices facade;

    public UpdateTrainerController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Update the information of a trainer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The trainer is updated correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PutMapping
    public ResponseEntity<UpdateTrainerResponse> updateTrainer(
            @Valid @RequestBody UpdateTrainerRequest request) {

        UpdateTrainerResponse response = facade.updateTrainer(request);

        return ResponseEntity.ok(response);
    }
}
