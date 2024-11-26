package com.shumisoft.employee_management_system.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.shumisoft.employee_management_system.entity.Department;
import com.shumisoft.employee_management_system.entity.Employee;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeResponseWithDepartmentDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate joinDate;
    private Employee.Status status;
    private Department department;
    private ManagerDTO manager;

    public static EmployeeResponseWithDepartmentDTO fromEntity(Employee entity) {

        return EmployeeResponseWithDepartmentDTO.builder().id(entity.getId()).firstName(entity.getFirstName())
                .lastName(entity.getLastName()).email(entity.getEmail()).phone(entity.getPhone())
                .joinDate(entity.getJoinDate()).status(entity.getStatus()).department(entity.getDepartment())
                .manager(ManagerDTO.fromEntity(entity.getManager())).build();

    }

}
