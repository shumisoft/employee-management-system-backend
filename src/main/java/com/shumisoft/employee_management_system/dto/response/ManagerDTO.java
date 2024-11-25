package com.shumisoft.employee_management_system.dto.response;

import java.util.UUID;

import com.shumisoft.employee_management_system.entity.Employee;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    public static ManagerDTO fromEntity(Employee entity) {
        if (entity == null)
            return null;

        return ManagerDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .build();
    }
}