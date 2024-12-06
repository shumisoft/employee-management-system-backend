package com.shumisoft.employee_management_system.dto.request;

import java.time.LocalDate;

import com.shumisoft.employee_management_system.entity.Employee;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeRequestDTO {

    @NotNull(message = "First Name cannot be null.")
    @NotEmpty(message = "First Name cannot be empty.")
    private String firstName;
    private String lastName;

    @NotNull(message = "email cannot be null.")
    @NotEmpty(message = "email cannot be empty.")
    private String email;
    private String phone;
    private LocalDate joinDate;
    private Employee.Status status;

    /**
     * Department of the employee. It corresponds to the Department entity, and is
     * represented by the department ID.
     */
    private Integer department;

    private Integer manager;

    public Employee toEntity() {

        return Employee.builder().firstName(firstName).lastName(lastName).email(email).phone(phone).joinDate(joinDate)
                .status(status).build();

    }

}
