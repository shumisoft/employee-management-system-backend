package com.shumisoft.employee_management_system.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shumisoft.employee_management_system.dto.request.EmployeePatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.EmployeeRequestDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseWithDepartmentDTO;
import com.shumisoft.employee_management_system.entity.Department;
import com.shumisoft.employee_management_system.entity.Employee;
import com.shumisoft.employee_management_system.repository.DepartmentRepository;
import com.shumisoft.employee_management_system.repository.EmployeeRepository;
import com.shumisoft.employee_management_system.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private Employee findEmployeeByIdOrThrowException(Integer id) {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

    }

    private Department findDepartmentByIdOrThrowException(Integer id) {

        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
    }

    @Override
    public EmployeeResponseWithDepartmentDTO createEmployee(EmployeeRequestDTO dto) {

        Employee entity = dto.toEntity();

        entity.setDepartment(findDepartmentByIdOrThrowException(dto.getDepartment()));

        return EmployeeResponseWithDepartmentDTO.fromEntity(employeeRepository.save(entity));
    }

    @Override
    public Page<EmployeeResponseDTO> getAllEmployees(Integer page, Integer pageSize) {

        return employeeRepository.findAll(PageRequest.of(page, pageSize)).map(EmployeeResponseDTO::fromEntity);

    }

    @Override
    public Page<EmployeeResponseDTO> getEmployeesByDepartmentId(Integer departmentId, Integer page,
            Integer pageSize) {

        return employeeRepository.findByDepartmentId(departmentId, PageRequest.of(page, pageSize))
                .map(EmployeeResponseDTO::fromEntity);

    }

    @Override
    public Page<EmployeeResponseDTO> getSubordinatesByManagerId(Integer managerId, Integer page, Integer pageSize) {
        return employeeRepository.findByManagerId(managerId, PageRequest.of(page, pageSize))
                .map(EmployeeResponseDTO::fromEntity);
    }

    @Override
    public EmployeeResponseWithDepartmentDTO getEmployeeById(Integer id) {

        return EmployeeResponseWithDepartmentDTO.fromEntity(findEmployeeByIdOrThrowException(id));

    }

    @Override
    public EmployeeResponseWithDepartmentDTO updateEmployeeById(Integer id, EmployeeRequestDTO dto) {

        Employee entity = findEmployeeByIdOrThrowException(id);

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setJoinDate(dto.getJoinDate());
        entity.setStatus(dto.getStatus());
        entity.setDepartment(findDepartmentByIdOrThrowException(dto.getDepartment()));

        return EmployeeResponseWithDepartmentDTO.fromEntity(employeeRepository.save(entity));

    }

    @Override
    public EmployeeResponseWithDepartmentDTO patchEmployeeById(Integer id, EmployeePatchRequestDTO dto) {

        Employee entity = findEmployeeByIdOrThrowException(id);

        if (dto.isEmpty()) {

            throw new IllegalArgumentException("At least one field must be provided for update.");

        }

        if (dto.getFirstName() != null) {

            entity.setFirstName(dto.getFirstName());

        }

        if (dto.getLastName() != null) {

            entity.setLastName(dto.getLastName());

        }

        if (dto.getEmail() != null) {

            entity.setEmail(dto.getEmail());

        }

        if (dto.getPhone() != null) {

            entity.setPhone(dto.getPhone());

        }

        if (dto.getJoinDate() != null) {

            entity.setJoinDate(dto.getJoinDate());

        }

        if (dto.getStatus() != null) {

            entity.setStatus(dto.getStatus());

        }

        if (dto.getDepartment() != null) {

            entity.setDepartment(findDepartmentByIdOrThrowException(dto.getDepartment()));

        }

        if (dto.getManager() != null) {

            entity.setManager(findEmployeeByIdOrThrowException(dto.getManager()));

        }

        return EmployeeResponseWithDepartmentDTO.fromEntity(employeeRepository.save(entity));
    }

    @Override
    public void deleteEmployeeById(Integer id) {

        employeeRepository.delete(findEmployeeByIdOrThrowException(id));

    }
}
