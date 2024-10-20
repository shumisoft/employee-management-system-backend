package com.shumisoft.employee_management_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumisoft.employee_management_system.dto.request.DepartmentPatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.DepartmentRequestDTO;
import com.shumisoft.employee_management_system.dto.response.DepartmentResponseDTO;
import com.shumisoft.employee_management_system.service.impl.DepartmentServiceImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentServiceImpl service;

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody @Valid DepartmentRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDepartment(dto));

    }

    @GetMapping
    public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartments(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(service.getAllDepartments(page, pageSize));

    }

    @GetMapping("{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Integer id) {

        return ResponseEntity.ok(service.getDepartmentById(id));

    }

    @PutMapping("{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartmentById(@PathVariable @Valid Integer id,
            @RequestBody DepartmentRequestDTO dto) {

        return ResponseEntity.ok(service.updateDepartmentById(id, dto));
    }

    @PatchMapping("{id}")
    public ResponseEntity<DepartmentResponseDTO> patchDepartmentById(@PathVariable Integer id,
            @RequestBody DepartmentPatchRequestDTO dto) {

        return ResponseEntity.ok(service.patchDepartmentById(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable Integer id) {

        service.deleteDepartmentById(id);

        return ResponseEntity.noContent().build();
    }

}
