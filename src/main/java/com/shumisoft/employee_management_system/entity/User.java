package com.shumisoft.employee_management_system.entity;

import com.shumisoft.employee_management_system.utils.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users") // avoid using reserved keyowrds
public class User {

    @Id
    String username;
    String passwordHash;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    UserRole role = UserRole.USER;

    @OneToOne
    Employee employee;

}
