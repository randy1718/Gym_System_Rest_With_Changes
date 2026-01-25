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

import com.gym.system.dto.ActivateDeactivateTraineeRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Activate deactivate trainee", description = "Endpoint to change the value of isActive variable of a trainee")
@RestController
@RequestMapping("/activateDeactivateTrainee")
public class ActivateDeactivateTraineeController {
    private final GymServices facade;

    public ActivateDeactivateTraineeController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Change the value of isActive variable of a trainee to true or false")
    @ApiResponses({
           @ApiResponse(responseCode = "200", description = "The value of Trainee's isActive is changed correctly"),
           @ApiResponse(responseCode = "400", description = "Invalid request data"),
           @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PatchMapping
    public ResponseEntity<Void> activateDeactivateTrainee(
            @Valid @RequestBody ActivateDeactivateTraineeRequest request) {

        Boolean authenticated = facade.activateDeactivateTrainee(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}    