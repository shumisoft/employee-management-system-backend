package com.shumisoft.employee_management_system.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shumisoft.employee_management_system.dto.request.DepartmentPatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.DepartmentRequestDTO;
import com.shumisoft.employee_management_system.dto.response.DepartmentResponseDTO;
import com.shumisoft.employee_management_system.entity.Department;
import com.shumisoft.employee_management_system.repository.DepartmentRepository;
import com.shumisoft.employee_management_system.service.DepartmentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    private Department findDepartmentByIdOrThrowException(Integer id) {

        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
    }

    @Override
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {

        return DepartmentResponseDTO.fromEntity(repository.save(dto.toEntity()));

    }

    @Override
    public Page<DepartmentResponseDTO> getAllDepartments(Integer page, Integer pageSize) {

        return repository.findAll(PageRequest.of(page, pageSize)).map(DepartmentResponseDTO::fromEntity);

    }

    @Override
    public DepartmentResponseDTO getDepartmentById(Integer id) {

        return DepartmentResponseDTO.fromEntity(findDepartmentByIdOrThrowException(id));

    }

    @Override
    public DepartmentResponseDTO updateDepartmentById(Integer id, DepartmentRequestDTO dto) {

        Department entity = findDepartmentByIdOrThrowException(id);

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return DepartmentResponseDTO.fromEntity(repository.save(entity));

    }

    @Override
    public DepartmentResponseDTO patchDepartmentById(Integer id, DepartmentPatchRequestDTO dto) {

        Department entity = findDepartmentByIdOrThrowException(id);

        if (dto.isEmpty()) {

            throw new IllegalArgumentException("At least one field must be provided for update.");

        }

        if (dto.getName() != null)
            entity.setName(dto.getName());

        if (dto.getDescription() != null)
            entity.setDescription(dto.getDescription());

        return DepartmentResponseDTO.fromEntity(repository.save(entity));

    }

    @Override
    public void deleteDepartmentById(Integer id) {

        repository.delete(findDepartmentByIdOrThrowException(id));

    }

}
