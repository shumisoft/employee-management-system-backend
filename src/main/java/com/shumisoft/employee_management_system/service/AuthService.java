package com.shumisoft.employee_management_system.service;

import com.shumisoft.employee_management_system.dto.request.ChangePassowordRequestDTO;
import com.shumisoft.employee_management_system.dto.request.LoginRequestDTO;
import com.shumisoft.employee_management_system.dto.request.RefreshTokenRequestDTO;
import com.shumisoft.employee_management_system.dto.request.RegisterRequestDTO;
import com.shumisoft.employee_management_system.dto.response.TokenResponseDTO;

public interface AuthService {

    public TokenResponseDTO register(RegisterRequestDTO dto);

    public TokenResponseDTO login(LoginRequestDTO dto);

    public TokenResponseDTO refresh(RefreshTokenRequestDTO dto);

    public void changePassword(String username, ChangePassowordRequestDTO dto);

}
