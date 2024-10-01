package com.shumisoft.employee_management_system.dto.response;

import com.shumisoft.employee_management_system.entity.Department;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepartmentResponseDTO {
    private Integer id;
    private String name;
    private String description;

    public static DepartmentResponseDTO fromEntity(Department entity) {
        return DepartmentResponseDTO.builder().id(entity.getId()).name(entity.getName())
                .description(entity.getDescription()).build();
    }
}
