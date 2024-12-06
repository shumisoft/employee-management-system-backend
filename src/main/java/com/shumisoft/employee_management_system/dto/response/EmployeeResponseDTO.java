package com.shumisoft.employee_management_system.dto.response;

import java.time.LocalDate;

import com.shumisoft.employee_management_system.entity.Employee;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeResponseDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate joinDate;
    private Employee.Status status;

    public static EmployeeResponseDTO fromEntity(Employee entity) {

        return EmployeeResponseDTO.builder().id(entity.getId()).firstName(entity.getFirstName())
                .lastName(entity.getLastName()).email(entity.getEmail()).phone(entity.getPhone())
                .joinDate(entity.getJoinDate()).status(entity.getStatus()).build();

    }

}
