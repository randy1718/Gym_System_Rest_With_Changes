package com.gym.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.ChangeLoginRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/changeLogin")
public class ChangeLoginController {
    private final GymServices facade;

    public ChangeLoginController(GymServices facade) {
        this.facade = facade;
    }

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