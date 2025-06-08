package com.shumisoft.employee_management_system.seeder;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.shumisoft.employee_management_system.entity.Employee;
import com.shumisoft.employee_management_system.entity.User;
import com.shumisoft.employee_management_system.repository.EmployeeRepository;
import com.shumisoft.employee_management_system.repository.UserRepository;
import com.shumisoft.employee_management_system.utils.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("[Seeder] UserSeeder seeder started");

        seedDemoAdmin();
        seedDemoEmployeeUser();

        log.info("[Seeder] UserSeeder seeder finished");

    }

    private void seedDemoAdmin() {

        String username = "demo-admin";

        if (userRepository.existsById(username)) {
            log.info("[Seeder] Admin already exists, skipping...");
            return;
        }

        User admin = User.builder()
                .username(username)
                .passwordHash(encoder.encode("DemoAdmin@123"))
                .role(UserRole.ADMIN)
                .build();

        userRepository.save(admin);

        log.info("[Seeder] Demo admin created!");
    }

    private void seedDemoEmployeeUser() {

        String email = "demo.employee@example.com";

        if (employeeRepository.existsByEmail(email)) {
            log.info("[Seeder] Demo employee already exists, skipping...");
            return;
        }

        Employee employee = Employee.builder()
                .firstName("Demo")
                .lastName("Employee")
                .email(email)
                .status(Employee.Status.ACTIVE)
                .joinDate(LocalDate.now())
                .build();

        employee = employeeRepository.save(employee);

        User user = User.builder()
                .username("demo-employee")
                .passwordHash(encoder.encode("DemoEmployee@123"))
                .role(UserRole.USER)
                .employee(employee)
                .build();

        userRepository.save(user);

        log.info("[Seeder] Demo employee user created with employeeId={}", employee.getId());
    }

}
