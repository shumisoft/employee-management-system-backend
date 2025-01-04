package com.shumisoft.employee_management_system.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {

    String username;
    Integer employeeId;
    String password;

}
