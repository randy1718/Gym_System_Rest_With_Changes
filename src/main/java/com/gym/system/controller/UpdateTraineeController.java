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

import com.gym.system.dto.UpdateTraineeRequest;
import com.gym.system.dto.UpdateTraineeResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Update a trainee", description = "Endpoint to update a trainee")
@RestController
@RequestMapping("/updateTrainee")
public class UpdateTraineeController {
    private final GymServices facade;

    public UpdateTraineeController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Update the information of a trainee")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The trainee is updated correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PutMapping
    public ResponseEntity<UpdateTraineeResponse> updateTrainee(
            @Valid @RequestBody UpdateTraineeRequest request) {

        UpdateTraineeResponse response = facade.updateTrainee(request);

        return ResponseEntity.ok(response);
    }
}
