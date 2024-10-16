package com.shumisoft.employee_management_system.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shumisoft.employee_management_system.dto.request.EmployeePatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.EmployeeRequestDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseDTO;
import com.shumisoft.employee_management_system.entity.Employee;
import com.shumisoft.employee_management_system.repository.EmployeeRepository;
import com.shumisoft.employee_management_system.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private Employee findEmployeeByIdOrThrowException(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

    }

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        return EmployeeResponseDTO.fromEntity(repository.save(dto.toEntity()));
    }

    @Override
    public Page<EmployeeResponseDTO> getAllEmployees(Integer page, Integer pageSize) {

        return repository.findAll(PageRequest.of(page, pageSize)).map(EmployeeResponseDTO::fromEntity);

    }

    @Override
    public EmployeeResponseDTO getEmployeeById(UUID id) {

        return EmployeeResponseDTO.fromEntity(findEmployeeByIdOrThrowException(id));

    }

    @Override
    public EmployeeResponseDTO updateEmployeeById(UUID id, EmployeeRequestDTO dto) {

        Employee entity = findEmployeeByIdOrThrowException(id);

        System.out.println(dto.getFirstName() + dto.getLastName());

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setJoinDate(dto.getJoinDate());
        entity.setStatus(dto.getStatus());

        Employee updated = repository.save(entity);

        return EmployeeResponseDTO.fromEntity(updated);

    }

    @Override
    public EmployeeResponseDTO patchEmployeeById(UUID id, EmployeePatchRequestDTO dto) {

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

        return EmployeeResponseDTO.fromEntity(entity);
    }

    @Override
    public void deleteEmployeeById(UUID id) {

        repository.delete(findEmployeeByIdOrThrowException(id));

    }
}
