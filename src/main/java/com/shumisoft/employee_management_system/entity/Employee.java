package com.shumisoft.employee_management_system.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Enum for Status
    public enum Status {
        ACTIVE,
        INACTIVE
    }

    @ManyToOne
    /**
     * default column name will be departmet + Department enttity pk
     * 
     * @JoinColumn(name = "emp_department_id") //custom column name
     */
    private Department department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

}
