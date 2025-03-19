package com.shumisoft.employee_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shumisoft.employee_management_system.dto.request.ChangePassowordRequestDTO;
import com.shumisoft.employee_management_system.dto.request.LoginRequestDTO;
import com.shumisoft.employee_management_system.dto.request.RefreshTokenRequestDTO;
import com.shumisoft.employee_management_system.dto.request.RegisterRequestDTO;
import com.shumisoft.employee_management_system.dto.response.TokenResponseDTO;
import com.shumisoft.employee_management_system.service.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthController {
    AuthService service;

    @PostMapping("register")
    public ResponseEntity<TokenResponseDTO> registerUser(@RequestBody @Valid RegisterRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.register(dto));

    }

    @PostMapping("login")
    public ResponseEntity<TokenResponseDTO> loginUser(@RequestBody @Valid LoginRequestDTO dto) {

        return ResponseEntity.ok(this.service.login(dto));

    }

    @PostMapping("refresh")
    public ResponseEntity<TokenResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO dto) {

        return ResponseEntity.ok(this.service.refresh(dto));

    }

    @PostMapping("change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassowordRequestDTO dto) {
        return ResponseEntity.ok("change-password endpoint working: " + dto);

    }

}
