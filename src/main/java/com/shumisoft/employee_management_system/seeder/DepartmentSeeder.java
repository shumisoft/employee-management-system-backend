package com.shumisoft.employee_management_system.seeder;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shumisoft.employee_management_system.entity.Department;
import com.shumisoft.employee_management_system.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DepartmentSeeder implements ApplicationRunner {

        private final DepartmentRepository repository;

        @Override
        public void run(ApplicationArguments args) {

                if (repository.count() == 0) {

                        List<Department> departments = List.of(
                                        Department.builder()
                                                        .name("Human Resources")
                                                        .description("Handles recruitment, onboarding, and employee relations")
                                                        .build(),
                                        Department.builder()
                                                        .name("Engineering")
                                                        .description("Responsible for product development and technical innovation")
                                                        .build(),
                                        Department.builder()
                                                        .name("Marketing")
                                                        .description("Manages branding, outreach, and lead generation")
                                                        .build(),
                                        Department.builder()
                                                        .name("Customer Support")
                                                        .description("Assists customers and handles service-related queries")
                                                        .build(),
                                        Department.builder()
                                                        .name("Finance")
                                                        .description("Manages budgeting, payroll, and financial reporting")
                                                        .build(),
                                        Department.builder()
                                                        .name("Sales")
                                                        .description("Focuses on client acquisition and revenue generation")
                                                        .build(),
                                        Department.builder()
                                                        .name("Legal")
                                                        .description("Handles legal matters, contracts, and compliance")
                                                        .build(),
                                        Department.builder()
                                                        .name("IT Services")
                                                        .description("Maintains infrastructure, security, and tech support")
                                                        .build(),
                                        Department.builder()
                                                        .name("Operations")
                                                        .description("Oversees daily activities and ensures business efficiency")
                                                        .build());

                        repository.saveAll(departments);

                }
        }
}
