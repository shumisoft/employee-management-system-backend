package com.shumisoft.employee_management_system.service;

import org.springframework.data.domain.Page;

import com.shumisoft.employee_management_system.dto.request.EmployeePatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.EmployeeRequestDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeOrgChartResponseDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseWithDepartmentDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeStatusCountResponseDTO;

public interface EmployeeService {

    public EmployeeResponseWithDepartmentDTO createEmployee(EmployeeRequestDTO dto);

    public Page<EmployeeResponseDTO> getAllEmployees(Integer page, Integer pageSize);

    public Page<EmployeeResponseDTO> getEmployeesByDepartmentId(Integer departmentId, Integer page,
            Integer pageSize);

    public Page<EmployeeResponseDTO> getSubordinatesByManagerId(Integer managerId, Integer page, Integer pageSize);

    public EmployeeOrgChartResponseDTO getEmployeeOrgChart(Integer employeeId, Integer page, Integer pageSize);

    public EmployeeResponseWithDepartmentDTO getEmployeeById(Integer id);

    public EmployeeResponseWithDepartmentDTO updateEmployeeById(Integer id, EmployeeRequestDTO dto);

    public EmployeeResponseWithDepartmentDTO patchEmployeeById(Integer id, EmployeePatchRequestDTO dto);

    public EmployeeStatusCountResponseDTO getEmployeeStatusCount();

    public void deleteEmployeeById(Integer id);

}
