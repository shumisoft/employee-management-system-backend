package com.shumisoft.employee_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shumisoft.employee_management_system.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
