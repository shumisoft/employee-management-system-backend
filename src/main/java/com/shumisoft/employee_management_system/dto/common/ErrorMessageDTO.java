package com.shumisoft.employee_management_system.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ErrorMessageDTO {
    private String message;
}
