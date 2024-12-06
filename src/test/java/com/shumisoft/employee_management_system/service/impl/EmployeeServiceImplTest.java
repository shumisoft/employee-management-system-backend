package com.shumisoft.employee_management_system.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.shumisoft.employee_management_system.dto.request.EmployeePatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.EmployeeRequestDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseDTO;
import com.shumisoft.employee_management_system.dto.response.EmployeeResponseWithDepartmentDTO;
import com.shumisoft.employee_management_system.entity.Department;
import com.shumisoft.employee_management_system.entity.Employee;
import com.shumisoft.employee_management_system.repository.DepartmentRepository;
import com.shumisoft.employee_management_system.repository.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;

class EmployeeServiceImplTest {

    private final EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
    private final DepartmentRepository departmentRepository = Mockito.mock(DepartmentRepository.class);
    private final EmployeeServiceImpl service = new EmployeeServiceImpl(employeeRepository, departmentRepository);

    private static final String JOHN_EMAIL = "john@example.com";

    @Test
    void createEmployeeSavesAndReturnsDTO() {

        // arrange
        Department department = new Department(1, "HR", "desc");
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("John")
                .email(JOHN_EMAIL)
                .department(1)
                .build();

        Employee savedEntity = dto.toEntity();
        savedEntity.setId(1);
        savedEntity.setDepartment(department);

        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEntity);

        // act
        EmployeeResponseWithDepartmentDTO result = service.createEmployee(dto);

        // assert
        assertNotNull(result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals(department, result.getDepartment());

    }

    @Test
    void createEmployeeDepartmentNotFoundThrowsException() {

        // arrange
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("John")
                .email(JOHN_EMAIL)
                .department(99)
                .build();

        when(departmentRepository.findById(99)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.createEmployee(dto));

    }

    @Test
    void getAllEmployeesReturnsPageOfDTOs() {

        // arrange
        Employee e = Employee.builder().id(1).firstName("Alice").email("a@b.com").build();
        when(employeeRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(e)));

        // act
        Page<EmployeeResponseDTO> result = service.getAllEmployees(0, 10);

        // assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Alice", result.getContent().get(0).getFirstName());

    }

    @Test
    void getEmployeesByDepartmentIdReturnsEmployees() {

        // arrange
        Employee e = Employee.builder().id(1).firstName("Bob").email("b@c.com").build();
        when(employeeRepository.findByDepartmentId(1, PageRequest.of(0, 5))).thenReturn(new PageImpl<>(List.of(e)));

        // act
        Page<EmployeeResponseDTO> result = service.getEmployeesByDepartmentId(1, 0, 5);

        // assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Bob", result.getContent().get(0).getFirstName());

    }

    @Test
    void getEmployeeByIdReturnsDTO() {

        // arrange
        Integer id = 1;
        Employee e = Employee.builder().id(id).firstName("Charlie").email("c@d.com").build();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(e));

        // act
        EmployeeResponseWithDepartmentDTO result = service.getEmployeeById(id);

        // assert
        assertEquals("Charlie", result.getFirstName());

    }

    @Test
    void getEmployeeByIdNotFoundThrowsException() {

        // arrange
        Integer id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.getEmployeeById(id));

    }

    @Test
    void updateEmployeeByIdUpdatesAndReturnsDTO() {

        // arrange
        Integer id = 1;
        Department department = new Department(1, "IT", "info");
        Employee existing = Employee.builder().id(id).firstName("Old").email("old@x.com").build();

        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("New")
                .email("new@x.com")
                .department(1)
                .build();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existing));
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        EmployeeResponseWithDepartmentDTO result = service.updateEmployeeById(id, dto);

        // assert
        assertEquals("New", result.getFirstName());
        assertEquals(department, result.getDepartment());

    }

    @Test
    void updateEmployeeByIdEmployeeNotFoundThrowsException() {

        // arrange
        Integer id = 1;
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("X")
                .email("x@y.com")
                .department(1)
                .build();

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.updateEmployeeById(id, dto));

    }

    @Test
    void updateEmployeeByIdDepartmentNotFoundThrowsException() {

        // arrange
        Integer id = 1;
        Employee existing = Employee.builder().id(id).firstName("Old").email("old@x.com").build();
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("New")
                .email("new@x.com")
                .department(99)
                .build();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existing));
        when(departmentRepository.findById(99)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.updateEmployeeById(id, dto));

    }

    @Test
    void patchEmployeeByIdUpdatesPartialFields() {

        // arrange
        Integer id = 1;
        Department dept = Department.builder().id(1).name("HR").description("Human Resources").build();

        Employee existing = Employee.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .email(JOHN_EMAIL)
                .phone("123")
                .joinDate(LocalDate.of(2020, 1, 1))
                .status(Employee.Status.ACTIVE)
                .department(dept)
                .build();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(inv -> inv.getArgument(0));
        when(departmentRepository.findById(2))
                .thenReturn(Optional.of(Department.builder().id(2).name("IT").build()));

        // act
        // patch firstName
        EmployeePatchRequestDTO patchFirstName = new EmployeePatchRequestDTO();
        patchFirstName.setFirstName("Jane");
        EmployeeResponseWithDepartmentDTO res = service.patchEmployeeById(id, patchFirstName);

        // assert
        assertEquals("Jane", res.getFirstName());
        assertEquals("Doe", res.getLastName());

        // patch lastName
        EmployeePatchRequestDTO patchLastName = new EmployeePatchRequestDTO();
        patchLastName.setLastName("Smith");
        res = service.patchEmployeeById(id, patchLastName);

        // assert
        assertEquals("Smith", res.getLastName());
        assertEquals("Jane", res.getFirstName());

        // patch email
        EmployeePatchRequestDTO patchEmail = new EmployeePatchRequestDTO();
        patchEmail.setEmail("jane.smith@example.com");
        res = service.patchEmployeeById(id, patchEmail);

        // assert
        assertEquals("jane.smith@example.com", res.getEmail());

        // patch phone
        EmployeePatchRequestDTO patchPhone = new EmployeePatchRequestDTO();
        patchPhone.setPhone("456");
        res = service.patchEmployeeById(id, patchPhone);

        // assert
        assertEquals("456", res.getPhone());

        // patch joinDate
        LocalDate newDate = LocalDate.of(2021, 5, 15);
        EmployeePatchRequestDTO patchJoinDate = new EmployeePatchRequestDTO();
        patchJoinDate.setJoinDate(newDate);
        res = service.patchEmployeeById(id, patchJoinDate);

        // assert
        assertEquals(newDate, res.getJoinDate());

        // patch status
        EmployeePatchRequestDTO patchStatus = new EmployeePatchRequestDTO();
        patchStatus.setStatus(Employee.Status.INACTIVE);
        res = service.patchEmployeeById(id, patchStatus);

        // assert
        assertEquals(Employee.Status.INACTIVE, res.getStatus());

        // patch department
        EmployeePatchRequestDTO patchDept = new EmployeePatchRequestDTO();
        patchDept.setDepartment(2);
        res = service.patchEmployeeById(id, patchDept);

        // assert
        assertEquals(2, res.getDepartment().getId());

        // patch multiple fields
        EmployeePatchRequestDTO patchAll = new EmployeePatchRequestDTO();
        patchAll.setFirstName("Alex");
        patchAll.setLastName("Johnson");
        patchAll.setEmail("alex@example.com");
        patchAll.setPhone("789");
        patchAll.setJoinDate(LocalDate.of(2022, 2, 2));
        patchAll.setStatus(Employee.Status.ACTIVE);
        patchAll.setDepartment(2);
        res = service.patchEmployeeById(id, patchAll);

        // assert
        assertEquals("Alex", res.getFirstName());
        assertEquals("Johnson", res.getLastName());
        assertEquals("alex@example.com", res.getEmail());
        assertEquals("789", res.getPhone());
        assertEquals(LocalDate.of(2022, 2, 2), res.getJoinDate());
        assertEquals(Employee.Status.ACTIVE, res.getStatus());
        assertEquals(2, res.getDepartment().getId());

    }

    @Test
    void patchEmployeeByIdEmptyPatchThrowsException() {

        // arrange
        Integer id = 1;
        Employee existing = Employee.builder().id(id).firstName("Old").build();
        EmployeePatchRequestDTO dto = new EmployeePatchRequestDTO();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existing));

        // assert
        assertThrows(IllegalArgumentException.class, () -> service.patchEmployeeById(id, dto));

    }

    @Test
    void patchEmployeeByIdEmployeeNotFoundThrowsException() {

        // arrange
        Integer id = 1;
        EmployeePatchRequestDTO dto = new EmployeePatchRequestDTO();
        dto.setFirstName("X");

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.patchEmployeeById(id, dto));

    }

    @Test
    void patchEmployeeByIdDepartmentNotFoundThrowsException() {

        // arrange
        Integer id = 1;
        Employee existing = Employee.builder().id(id).firstName("Old").build();
        EmployeePatchRequestDTO dto = new EmployeePatchRequestDTO();
        dto.setDepartment(99);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existing));
        when(departmentRepository.findById(99)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.patchEmployeeById(id, dto));

    }

    @Test
    void deleteEmployeeByIdDeletesSuccessfully() {

        // arrange
        Integer id = 1;
        Employee e = Employee.builder().id(id).firstName("Del").build();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(e));

        // act
        service.deleteEmployeeById(id);

        // assert
        verify(employeeRepository).delete(e);

    }

    @Test
    void deleteEmployeeByIdNotFoundThrowsException() {

        // arrange
        Integer id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.deleteEmployeeById(id));

    }
}
