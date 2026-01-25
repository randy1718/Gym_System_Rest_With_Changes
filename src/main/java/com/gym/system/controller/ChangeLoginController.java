package com.gym.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.ChangeLoginRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@Tag(name = "Change login", description = "Endpoint to change the password or a trainee or trainer")
@RestController
@RequestMapping("/changeLogin")
public class ChangeLoginController {
    private final GymServices facade;

    public ChangeLoginController(GymServices facade) {
        this.facade = facade;
    }

    @Operation(description = "Change the password of a trainee or trainer by providing the old one and a new one.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The password is changed correctly"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Invalid server error")
    })
    @PutMapping
    public ResponseEntity<Void> changeLogin(
            @Valid @RequestBody ChangeLoginRequest request) {

        Boolean authenticated = facade.changeLogin(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}    