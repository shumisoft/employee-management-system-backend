package com.shumisoft.employee_management_system.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import com.shumisoft.employee_management_system.entity.Employee;

import lombok.Data;

@Data
public class EmployeePatchRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate joinDate;
    private Employee.Status status;
    private Integer department;
    private UUID manager;

    public boolean isEmpty() {

        return firstName == null && lastName == null && email == null && phone == null && joinDate == null
                && status == null && department == null;

    }

}
