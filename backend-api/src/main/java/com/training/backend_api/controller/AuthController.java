package com.training.backend_api.controller;

import  org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.backend_api.dto.LoginRequest;
import com.training.backend_api.service.KeycloakService;

import lombok.RequiredArgsConstructor;

@RestController

@RequestMapping("/api/auth")

@RequiredArgsConstructor

@CrossOrigin(
        origins =
        "http://localhost:4200"
)
public class AuthController {

    private final KeycloakService
            keycloakService;

    @PostMapping("/login")

    public ResponseEntity<?> login(

            @RequestBody
            LoginRequest request
    ) {

        return ResponseEntity.ok(

                keycloakService.login(

                        request.getUsername(),

                        request.getPassword()
                )
        );
    }
}