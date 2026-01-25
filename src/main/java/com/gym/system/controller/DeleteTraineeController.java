package com.gym.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.DeleteTraineeRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Delete trainee", description = "Endpoint to delete a trainee")
@RestController
@RequestMapping("/deleteTrainee")
public class DeleteTraineeController {
    private final GymServices facade;

    public DeleteTraineeController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Delete a trainee by providing the respective user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The trainee is deleted correctly."),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTrainee(
            @Valid @RequestBody DeleteTraineeRequest request) {

        Boolean authenticated = facade.deleteTrainee(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}    