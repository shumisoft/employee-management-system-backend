package com.shumisoft.employee_management_system.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.shumisoft.employee_management_system.dto.request.EmployeePatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.EmployeeRequestDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseDTO;

public interface EmployeeService {

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);

    public Page<EmployeeResponseDTO> getAllEmployees(Integer page, Integer pageSize);

    public EmployeeResponseDTO getEmployeeById(UUID id);

    public EmployeeResponseDTO updateEmployeeById(UUID id, EmployeeRequestDTO dto);

    public EmployeeResponseDTO patchEmployeeById(UUID Id, EmployeePatchRequestDTO dto);

    public void deleteEmployeeById(UUID id);

}
