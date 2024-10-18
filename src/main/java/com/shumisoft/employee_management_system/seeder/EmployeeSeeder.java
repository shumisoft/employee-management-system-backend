package com.shumisoft.employee_management_system.seeder;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shumisoft.employee_management_system.entity.Employee;
import com.shumisoft.employee_management_system.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeSeeder implements ApplicationRunner {

    private final EmployeeRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (repository.count() == 0) {

            List<Employee> employees = List.of(
                    Employee.builder().firstName("John").lastName("Doe")
                            .email("john.doe@example.com").phone("555-1234").joinDate(LocalDate.of(2020, 1, 15))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("Jane").lastName("Smith")
                            .email("jane.smith@example.com").phone("555-5678").joinDate(LocalDate.of(2019, 3, 22))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("Alice").lastName("Johnson")
                            .email("alice.johnson@example.com").phone("555-8765").joinDate(LocalDate.of(2021, 7, 10))
                            .status(Employee.Status.INACTIVE).build(),
                    Employee.builder().firstName("Bob").lastName("Brown")
                            .email("bob.brown@example.com").phone("555-4321").joinDate(LocalDate.of(2022, 9, 5))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("Charlie").lastName("Davis")
                            .email("charlie.davis@example.com").phone("555-9988").joinDate(LocalDate.of(2020, 5, 30))
                            .status(Employee.Status.INACTIVE).build(),
                    Employee.builder().firstName("David").lastName("Miller")
                            .email("david.miller@example.com").phone("555-2233").joinDate(LocalDate.of(2018, 8, 14))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("Eva").lastName("Wilson")
                            .email("eva.wilson@example.com").phone("555-6655").joinDate(LocalDate.of(2023, 2, 19))
                            .status(Employee.Status.INACTIVE).build(),
                    Employee.builder().firstName("Frank").lastName("Moore")
                            .email("frank.moore@example.com").phone("555-4455").joinDate(LocalDate.of(2021, 11, 11))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("Grace").lastName("Taylor")
                            .email("grace.taylor@example.com").phone("555-1122").joinDate(LocalDate.of(2020, 4, 12))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("Henry").lastName("Anderson")
                            .email("henry.anderson@example.com").phone("555-3344").joinDate(LocalDate.of(2019, 6, 27))
                            .status(Employee.Status.INACTIVE).build(),
                    Employee.builder().firstName("Isla").lastName("Martinez")
                            .email("isla.martinez@example.com").phone("555-8888").joinDate(LocalDate.of(2021, 3, 10))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("James").lastName("Garcia")
                            .email("james.garcia@example.com").phone("555-7777").joinDate(LocalDate.of(2019, 10, 1))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("Lily").lastName("Rodriguez")
                            .email("lily.rodriguez@example.com").phone("555-1230").joinDate(LocalDate.of(2022, 6, 15))
                            .status(Employee.Status.INACTIVE).build(),
                    Employee.builder().firstName("Mason").lastName("Martinez")
                            .email("mason.martinez@example.com").phone("555-9999").joinDate(LocalDate.of(2021, 4, 21))
                            .status(Employee.Status.ACTIVE).build(),
                    Employee.builder().firstName("Nina").lastName("Hernandez")
                            .email("nina.hernandez@example.com").phone("555-7770").joinDate(LocalDate.of(2020, 12, 18))
                            .status(Employee.Status.ACTIVE).build());

            repository.saveAll(employees);
        }
    }
}
