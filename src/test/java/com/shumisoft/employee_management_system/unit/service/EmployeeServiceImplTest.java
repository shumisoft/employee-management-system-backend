package com.shumisoft.employee_management_system.unit.service;

import static com.shumisoft.employee_management_system.base.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import com.shumisoft.employee_management_system.service.impl.EmployeeServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeServiceImpl service;

    // shared test data — built fresh before each test
    private Department hrDept;
    private Department itDept;
    private Employee existing;

    @BeforeEach
    void setUp() {
        hrDept = hrDepartment();
        itDept = itDepartment();
        existing = defaultEmployee(hrDept);
    }

    @Test
    void createEmployeeSavesAndReturnsDTO() {
        // arrange
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("John")
                .email(JOHN_EMAIL)
                .department(EXISTING_ID)
                .build();

        Employee savedEntity = dto.toEntity();
        savedEntity.setId(EXISTING_ID);
        savedEntity.setDepartment(hrDept);

        when(departmentRepository.findById(EXISTING_ID)).thenReturn(Optional.of(hrDept));
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEntity);

        // act
        EmployeeResponseWithDepartmentDTO result = service.createEmployee(dto);

        // assert
        assertNotNull(result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals(hrDept, result.getDepartment());
    }

    @Test
    void createEmployeeDepartmentNotFoundThrowsException() {
        // arrange
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("John")
                .email(JOHN_EMAIL)
                .department(NOT_FOUND_ID)
                .build();

        when(departmentRepository.findById(NOT_FOUND_ID)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.createEmployee(dto));
    }

    // -------------------------------------------------------------------------
    // READ
    // -------------------------------------------------------------------------

    @Test
    void getAllEmployeesReturnsPageOfDTOs() {
        // arrange
        when(employeeRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(existing)));

        // act
        Page<EmployeeResponseDTO> result = service.getAllEmployees(0, 10);

        // assert
        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
    }

    @Test
    void getEmployeesByDepartmentIdReturnsEmployees() {
        // arrange
        when(employeeRepository.findByDepartmentId(EXISTING_ID, PageRequest.of(0, 5)))
                .thenReturn(new PageImpl<>(List.of(existing)));

        // act
        Page<EmployeeResponseDTO> result = service.getEmployeesByDepartmentId(EXISTING_ID, 0, 5);

        // assert
        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
    }

    @Test
    void getEmployeeByIdReturnsDTO() {
        // arrange
        when(employeeRepository.findById(EXISTING_ID)).thenReturn(Optional.of(existing));

        // act
        EmployeeResponseWithDepartmentDTO result = service.getEmployeeById(EXISTING_ID);

        // assert
        assertEquals("John", result.getFirstName());
    }

    @Test
    void getEmployeeByIdNotFoundThrowsException() {
        // arrange
        when(employeeRepository.findById(NOT_FOUND_ID)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.getEmployeeById(NOT_FOUND_ID));
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------

    @Test
    void updateEmployeeByIdUpdatesAndReturnsDTO() {
        // arrange
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("New")
                .email("new@x.com")
                .department(EXISTING_ID)
                .build();

        when(employeeRepository.findById(EXISTING_ID)).thenReturn(Optional.of(existing));
        when(departmentRepository.findById(EXISTING_ID)).thenReturn(Optional.of(hrDept));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(inv -> inv.getArgument(0));

        // act
        EmployeeResponseWithDepartmentDTO result = service.updateEmployeeById(EXISTING_ID, dto);

        // assert
        assertEquals("New", result.getFirstName());
        assertEquals(hrDept, result.getDepartment());
    }

    @Test
    void updateEmployeeByIdEmployeeNotFoundThrowsException() {
        // arrange
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("X").email("x@y.com").department(EXISTING_ID).build();

        when(employeeRepository.findById(EXISTING_ID)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.updateEmployeeById(EXISTING_ID, dto));
    }

    @Test
    void updateEmployeeByIdDepartmentNotFoundThrowsException() {
        // arrange
        EmployeeRequestDTO dto = EmployeeRequestDTO.builder()
                .firstName("New").email("new@x.com").department(NOT_FOUND_ID).build();

        when(employeeRepository.findById(EXISTING_ID)).thenReturn(Optional.of(existing));
        when(departmentRepository.findById(NOT_FOUND_ID)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.updateEmployeeById(EXISTING_ID, dto));
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
