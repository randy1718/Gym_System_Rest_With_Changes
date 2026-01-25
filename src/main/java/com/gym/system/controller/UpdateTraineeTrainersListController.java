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

import com.gym.system.dto.UpdateTrainersListRequest;
import com.gym.system.dto.UpdateTrainersListResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Update trainee's trainer list", description = "Endpoint to update the list of trainers of a trainee")
@RestController
@RequestMapping("/updateTraineeTrainersList")
public class UpdateTraineeTrainersListController {
    private final GymServices facade;

    public UpdateTraineeTrainersListController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Update trainee's trainers list by providing the trainer username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The trainee's trainers list is updated correctly."),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PutMapping
    public ResponseEntity<UpdateTrainersListResponse> UpdateTraineeTrainersList(
            @Valid @RequestBody UpdateTrainersListRequest request) {

        UpdateTrainersListResponse response = facade.UpdateTraineeTrainersList(request);

        return ResponseEntity.ok(response);
    }
}
