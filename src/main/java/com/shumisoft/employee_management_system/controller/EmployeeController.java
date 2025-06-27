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

import com.shumisoft.employee_management_system.dto.request.EmployeePatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.EmployeeRequestDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeOrgChartResponseDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseWithDepartmentDTO;
import com.shumisoft.employee_management_system.service.impl.EmployeeServiceImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/employee")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl service;

    @PostMapping
    public ResponseEntity<EmployeeResponseWithDepartmentDTO> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createEmployee(dto));

    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        if (departmentId != null) {

            return ResponseEntity.ok(service.getEmployeesByDepartmentId(departmentId, page, pageSize));

        }

        return ResponseEntity.ok(service.getAllEmployees(page, pageSize));

    }

    @GetMapping("{id}/subordinates")
    public ResponseEntity<Page<EmployeeResponseDTO>> getSubordinatesByManagerId(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(service.getSubordinatesByManagerId(id, page, pageSize));
    }

    @GetMapping("{id}/org-chart")
    public ResponseEntity<EmployeeOrgChartResponseDTO> getEmployeeOrgChart(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(
                service.getEmployeeOrgChart(id, page, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeResponseWithDepartmentDTO> getEmployeeById(@PathVariable Integer id) {

        return ResponseEntity.ok(service.getEmployeeById(id));

    }

    @PutMapping("{id}")
    public ResponseEntity<EmployeeResponseWithDepartmentDTO> updateEmployeeById(@PathVariable Integer id,
            @RequestBody EmployeeRequestDTO dto) {

        return ResponseEntity.ok(service.updateEmployeeById(id, dto));

    }

    @PatchMapping("{id}")
    public ResponseEntity<EmployeeResponseWithDepartmentDTO> patchEmployeeById(@PathVariable Integer id,
            @RequestBody EmployeePatchRequestDTO dto) {

        return ResponseEntity.ok(service.patchEmployeeById(id, dto));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Integer id) {

        service.deleteEmployeeById(id);

        return ResponseEntity.noContent().build();

    }

}
