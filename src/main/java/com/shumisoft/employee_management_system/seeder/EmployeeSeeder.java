package com.shumisoft.employee_management_system.seeder;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shumisoft.employee_management_system.entity.Employee;
import com.shumisoft.employee_management_system.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeSeeder implements ApplicationRunner {

    private final EmployeeRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("[Seeder] EmployeeSeeder started");

        if (repository.count() == 0) {

            log.info("[Seeder] No employees found. Seeding sample employees...");

            seedSampleEmployees();

        } else {

            log.info("[Seeder] Employees already exist. Skipping sample employee seeding.");

        }

    }

    private void seedSampleEmployees() {
        List<Employee> employees = List.of(
                Employee.builder().firstName("John").lastName("Doe")
                        .email("john.doe@example.com").phone("555-1234")
                        .joinDate(LocalDate.of(2020, 1, 15))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Jane").lastName("Smith")
                        .email("jane.smith@example.com").phone("555-5678")
                        .joinDate(LocalDate.of(2019, 3, 22))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Alice").lastName("Johnson")
                        .email("alice.johnson@example.com").phone("555-8765")
                        .joinDate(LocalDate.of(2021, 7, 10))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Bob").lastName("Brown")
                        .email("bob.brown@example.com").phone("555-4321")
                        .joinDate(LocalDate.of(2022, 9, 5))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Charlie").lastName("Davis")
                        .email("charlie.davis@example.com").phone("555-9988")
                        .joinDate(LocalDate.of(2020, 5, 30))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("David").lastName("Miller")
                        .email("david.miller@example.com").phone("555-2233")
                        .joinDate(LocalDate.of(2018, 8, 14))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Eva").lastName("Wilson")
                        .email("eva.wilson@example.com").phone("555-6655")
                        .joinDate(LocalDate.of(2023, 2, 19))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Frank").lastName("Moore")
                        .email("frank.moore@example.com").phone("555-4455")
                        .joinDate(LocalDate.of(2021, 11, 11))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Grace").lastName("Taylor")
                        .email("grace.taylor@example.com").phone("555-1122")
                        .joinDate(LocalDate.of(2020, 4, 12))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Henry").lastName("Anderson")
                        .email("henry.anderson@example.com").phone("555-3344")
                        .joinDate(LocalDate.of(2019, 6, 27))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Isla").lastName("Martinez")
                        .email("isla.martinez@example.com").phone("555-8888")
                        .joinDate(LocalDate.of(2021, 3, 10))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("James").lastName("Garcia")
                        .email("james.garcia@example.com").phone("555-7777")
                        .joinDate(LocalDate.of(2019, 10, 1))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Lily").lastName("Rodriguez")
                        .email("lily.rodriguez@example.com").phone("555-1230")
                        .joinDate(LocalDate.of(2022, 6, 15))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Mason").lastName("Martinez")
                        .email("mason.martinez@example.com").phone("555-9999")
                        .joinDate(LocalDate.of(2021, 4, 21))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Nina").lastName("Hernandez")
                        .email("nina.hernandez@example.com").phone("555-7770")
                        .joinDate(LocalDate.of(2020, 12, 18))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Olivia").lastName("Lopez")
                        .email("olivia.lopez@example.com").phone("555-1010")
                        .joinDate(LocalDate.of(2023, 1, 20))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Peter").lastName("Perez")
                        .email("peter.perez@example.com").phone("555-2020")
                        .joinDate(LocalDate.of(2022, 8, 11))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Quinn").lastName("Ramirez")
                        .email("quinn.ramirez@example.com").phone("555-3030")
                        .joinDate(LocalDate.of(2021, 5, 5))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Rachel").lastName("Foster")
                        .email("rachel.foster@example.com").phone("555-4040")
                        .joinDate(LocalDate.of(2020, 10, 25))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Sam").lastName("Washington")
                        .email("sam.washington@example.com").phone("555-5050")
                        .joinDate(LocalDate.of(2022, 3, 17))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Tina").lastName("Cooper")
                        .email("tina.cooper@example.com").phone("555-6060")
                        .joinDate(LocalDate.of(2019, 1, 30))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Ulysses").lastName("Morgan")
                        .email("ulysses.morgan@example.com").phone("555-7070")
                        .joinDate(LocalDate.of(2023, 4, 9))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Victoria").lastName("Bell")
                        .email("victoria.bell@example.com").phone("555-8080")
                        .joinDate(LocalDate.of(2021, 9, 2))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Walter").lastName("Brooks")
                        .email("walter.brooks@example.com").phone("555-9090")
                        .joinDate(LocalDate.of(2020, 7, 21))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Xena").lastName("Kelly")
                        .email("xena.kelly@example.com").phone("555-1111")
                        .joinDate(LocalDate.of(2022, 11, 14))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Yara").lastName("Ross")
                        .email("yara.ross@example.com").phone("555-2222")
                        .joinDate(LocalDate.of(2018, 5, 1))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Zane").lastName("Barnes")
                        .email("zane.barnes@example.com").phone("555-3333")
                        .joinDate(LocalDate.of(2023, 6, 28))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Alex").lastName("Stewart")
                        .email("alex.stewart@example.com").phone("555-4444")
                        .joinDate(LocalDate.of(2021, 2, 18))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Bethany").lastName("Price")
                        .email("bethany.price@example.com").phone("555-5555")
                        .joinDate(LocalDate.of(2020, 8, 7))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Caleb").lastName("White")
                        .email("caleb.white@example.com").phone("555-6666")
                        .joinDate(LocalDate.of(2022, 1, 3))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Diana").lastName("King")
                        .email("diana.king@example.com").phone("555-7777")
                        .joinDate(LocalDate.of(2019, 11, 22))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Ethan").lastName("Green")
                        .email("ethan.green@example.com").phone("555-8888")
                        .joinDate(LocalDate.of(2023, 3, 14))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Fiona").lastName("Hall")
                        .email("fiona.hall@example.com").phone("555-9990")
                        .joinDate(LocalDate.of(2020, 6, 1))
                        .status(Employee.Status.ACTIVE).build(),
                Employee.builder().firstName("Gabriel").lastName("Baker")
                        .email("gabriel.baker@example.com").phone("555-1000")
                        .joinDate(LocalDate.of(2021, 10, 29))
                        .status(Employee.Status.INACTIVE).build(),
                Employee.builder().firstName("Hannah").lastName("Wright")
                        .email("hannah.wright@example.com").phone("555-2000")
                        .joinDate(LocalDate.of(2022, 5, 20))
                        .status(Employee.Status.ACTIVE).build()

        );

        repository.saveAll(employees);

        log.info("[Seeder] Seeded {} sample employees", employees.size());

    }

}
