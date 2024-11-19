package com.shumisoft.employee_management_system.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shumisoft.employee_management_system.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Page<Employee> findByDepartmentId(Integer departmentId, Pageable pageable);

    Page<Employee> findByManagerId(UUID managerId, Pageable pageable);

}
