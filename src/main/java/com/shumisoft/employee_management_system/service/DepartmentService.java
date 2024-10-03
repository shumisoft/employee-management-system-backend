package com.shumisoft.employee_management_system.service;

import org.springframework.data.domain.Page;

import com.shumisoft.employee_management_system.dto.request.DepartmentPatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.DepartmentRequestDTO;
import com.shumisoft.employee_management_system.dto.response.DepartmentResponseDTO;

public interface DepartmentService {

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto);

    public Page<DepartmentResponseDTO> getAllDepartments(Integer page, Integer pageSize);

    public DepartmentResponseDTO getDepartmentById(Integer id);

    public DepartmentResponseDTO updateDepartmentById(Integer id, DepartmentRequestDTO dto);

    public DepartmentResponseDTO patchDepartmentById(Integer id, DepartmentPatchRequestDTO dto);

    public void deleteDepartmentById(Integer id);
}
