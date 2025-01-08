package com.shumisoft.employee_management_system.service.impl;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shumisoft.employee_management_system.dto.request.ChangePassowordRequestDTO;
import com.shumisoft.employee_management_system.dto.request.LoginRequestDTO;
import com.shumisoft.employee_management_system.dto.request.RefreshTokenRequestDTO;
import com.shumisoft.employee_management_system.dto.request.RegisterRequestDTO;
import com.shumisoft.employee_management_system.dto.response.TokenResponseDTO;
import com.shumisoft.employee_management_system.entity.Employee;
import com.shumisoft.employee_management_system.entity.User;
import com.shumisoft.employee_management_system.exception.InvalidTokenException;
import com.shumisoft.employee_management_system.repository.EmployeeRepository;
import com.shumisoft.employee_management_system.repository.UserRepository;
import com.shumisoft.employee_management_system.service.AuthService;
import com.shumisoft.employee_management_system.utils.JwtUtil;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public TokenResponseDTO register(RegisterRequestDTO dto) {

        Employee employee = null;

        if (dto.getEmployeeId() != null) {

            employee = this.employeeRepository.findById(dto.getEmployeeId()).orElseThrow(
                    () -> new EntityNotFoundException("No employee found with id: " + dto.getEmployeeId()));

            if (this.userRepository.existsByEmployeeId(dto.getEmployeeId())) {

                throw new EntityExistsException("User already exists with employeeId: " + dto.getEmployeeId());

            }

        }

        if (this.userRepository.existsById(dto.getUsername())) {

            throw new EntityExistsException("User already exists with username: " + dto.getUsername());

        }

        User user = userRepository.save(User.builder().username(dto.getUsername())
                .passwordHash(encoder.encode(dto.getPassword())).employee(employee).build());

        return this.jwtUtil.generateTokens(user);

    }

    @Override
    public TokenResponseDTO login(LoginRequestDTO dto) {

        User entity = this.userRepository.findById(dto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + dto.getUsername()));

        if (!encoder.matches(dto.getPassword(), entity.getPasswordHash())) {

            throw new BadCredentialsException("Invalid password.");
        }

        return this.jwtUtil.generateTokens(entity);

    }

    @Override
    public TokenResponseDTO refresh(RefreshTokenRequestDTO dto) {

        if (!this.jwtUtil.isTokenValid(dto.getRefreshToken())) {

            throw new InvalidTokenException("Invalid or expired token");

        }

        User entity = this.userRepository.findById(this.jwtUtil.extractUsername(dto.getRefreshToken()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return this.jwtUtil.generateTokens(entity);

    }

    @Override
    public void changePassword(String username, ChangePassowordRequestDTO dto) {

        User entity = this.userRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        if (!encoder.matches(dto.getOldPassword(), entity.getPasswordHash())) {

            throw new BadCredentialsException("Invalid password.");
        }

        entity.setPasswordHash(encoder.encode(dto.getNewPassword()));

        this.userRepository.save(entity);

    }

}
