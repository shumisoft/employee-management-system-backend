package com.shumisoft.employee_management_system.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDTO {

    String token;
    String refreshToken;

}
