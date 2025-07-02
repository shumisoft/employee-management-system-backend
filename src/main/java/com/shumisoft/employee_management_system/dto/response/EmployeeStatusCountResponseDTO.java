package com.shumisoft.employee_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EmployeeStatusCountResponseDTO {

  private long active;
  private long inactive;

}
