package com.shumisoft.employee_management_system.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePassowordRequestDTO {

    String oldPassword;
    String newPassword;

}
