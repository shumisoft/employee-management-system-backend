package com.shumisoft.employee_management_system.base;

import java.time.LocalDate;

import com.shumisoft.employee_management_system.entity.Department;
import com.shumisoft.employee_management_system.entity.Employee;

public class TestDataFactory {

  // IDs
  public static final Integer EXISTING_ID = 1;
  public static final Integer NOT_FOUND_ID = 99;

  // Emails
  public static final String JOHN_EMAIL = "john@example.com";

  // Departments
  public static Department hrDepartment() {
    return Department.builder()
        .id(1)
        .name("HR")
        .description("Human Resources")
        .build();
  }

  public static Department itDepartment() {
    return Department.builder()
        .id(2)
        .name("IT")
        .description("Information Tech")
        .build();
  }

  // Employees
  public static Employee defaultEmployee(Department dept) {
    return Employee.builder()
        .id(EXISTING_ID)
        .firstName("John")
        .lastName("Doe")
        .email(JOHN_EMAIL)
        .phone("123")
        .joinDate(LocalDate.of(2020, 1, 1))
        .status(Employee.Status.ACTIVE)
        .department(dept)
        .build();
  }

}
