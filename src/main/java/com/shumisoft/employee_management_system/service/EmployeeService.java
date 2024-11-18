package com.shumisoft.employee_management_system.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.shumisoft.employee_management_system.dto.request.EmployeePatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.EmployeeRequestDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseWithDepartmentDTO;

public interface EmployeeService {

    public EmployeeResponseWithDepartmentDTO createEmployee(EmployeeRequestDTO dto);

    public Page<EmployeeResponseDTO> getAllEmployees(Integer page, Integer pageSize);

    public Page<EmployeeResponseDTO> getEmployeesByDepartmentId(Integer departmentId, Integer page,
            Integer pageSize);

    public Page<EmployeeResponseDTO> getSubordinatesByManagerId(UUID managerId, Integer page, Integer pageSize);

    public EmployeeResponseWithDepartmentDTO getEmployeeById(UUID id);

    public EmployeeResponseWithDepartmentDTO updateEmployeeById(UUID id, EmployeeRequestDTO dto);

    public EmployeeResponseWithDepartmentDTO patchEmployeeById(UUID id, EmployeePatchRequestDTO dto);

    public void deleteEmployeeById(UUID id);

}
