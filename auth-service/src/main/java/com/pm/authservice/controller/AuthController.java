package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController
{
    @Autowired
    private com.pm.authservice.service.Authservice authservice;


    @Operation(summary = "generate token on user Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        Optional<String> tokenOptional=authservice.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty())
        {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token=tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
