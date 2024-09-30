package com.shumisoft.employee_management_system.dto.request;

import com.shumisoft.employee_management_system.entity.Department;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartmentRequestDTO {
    @NotNull(message = "Department name is required.")
    @NotEmpty(message = "Department cannot be empty.")
    private String name;
    private String description;

    public Department toEntity() {
        return Department.builder().name(name).description(description).build();
    }
}
