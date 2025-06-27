package com.shumisoft.employee_management_system.dto.response;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeOrgChartResponseDTO {

  private EmployeeResponseDTO manager;
  private EmployeeResponseWithDepartmentDTO employee;
  private Page<EmployeeResponseDTO> subordinates;

}
