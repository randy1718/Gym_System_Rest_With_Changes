package com.gym.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.ActivateDeactivateTrainerRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Activate deactivate trainer", description = "Endpoint to change the value of isActive variable of a trainer")
@RestController
@RequestMapping("/activateDeactivateTrainer")
public class ActivateDeactivateTrainerController {
    private final GymServices facade;

    public ActivateDeactivateTrainerController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Change the value of isActive variable of a trainer to true or false")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The value of Trainer's isActive is changed correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PatchMapping
    public ResponseEntity<Void> activateDeactivateTrainer(
            @Valid @RequestBody ActivateDeactivateTrainerRequest request) {

        Boolean authenticated = facade.activateDeactivateTrainer(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}    