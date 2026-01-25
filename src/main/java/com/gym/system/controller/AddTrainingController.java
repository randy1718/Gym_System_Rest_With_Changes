package com.gym.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.AddTrainingRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Add a training", description = "Endpoint to add a training")
@RestController
@RequestMapping("/addTraining")
public class AddTrainingController {
    private final GymServices facade;

    public AddTrainingController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Add a training with the data provided")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The training is created correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PostMapping
    public ResponseEntity<Void> addTraining(
            @Valid @RequestBody AddTrainingRequest request) {

        Boolean authenticated = facade.addTraining(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}    