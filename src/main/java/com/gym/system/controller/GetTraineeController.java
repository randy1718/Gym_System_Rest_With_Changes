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

import com.gym.system.dto.TraineeProfileRequest;
import com.gym.system.dto.TraineeProfileResponse;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Get trainee's information", description = "Endpoint to get the information of a trainee")
@RestController
@RequestMapping("/getTrainee")
public class GetTraineeController {
    private final GymServices facade;

    public GetTraineeController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Get the information of a trainee by providing their username and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The information of the trainee is retrieved correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @GetMapping
    public ResponseEntity<TraineeProfileResponse> getTrainee(
            @Valid @RequestBody TraineeProfileRequest request) {

        TraineeProfileResponse response = facade.getTrainee(request);

        return ResponseEntity.ok(response);
    }
}
