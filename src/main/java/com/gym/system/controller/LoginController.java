package com.gym.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.system.dto.LoginRequest;
import com.gym.system.service.GymServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final GymServices facade;

    public LoginController(GymServices facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest request) {

        Boolean authenticated = facade.login(request);

        if(authenticated){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}    