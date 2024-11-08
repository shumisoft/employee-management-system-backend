package com.shumisoft.employee_management_system.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.shumisoft.employee_management_system.dto.request.DepartmentPatchRequestDTO;
import com.shumisoft.employee_management_system.dto.request.DepartmentRequestDTO;
import com.shumisoft.employee_management_system.dto.response.DepartmentResponseDTO;
import com.shumisoft.employee_management_system.entity.Department;
import com.shumisoft.employee_management_system.repository.DepartmentRepository;

import jakarta.persistence.EntityNotFoundException;

class DepartmentServiceImplTest {

    private final DepartmentRepository repository = Mockito.mock(DepartmentRepository.class);
    private final DepartmentServiceImpl service = new DepartmentServiceImpl(repository);

    @Test
    void createDepartment_savesAndReturnsDTO() {

        // arrange
        DepartmentRequestDTO req = new DepartmentRequestDTO();
        req.setName("Write test");
        req.setDescription("Something that reallydoesnt matter");

        Department saved = Department.builder().id(1).name("Entity test")
                .description("Something that somewhat matters").build();

        when(repository.save(any(Department.class))).thenReturn(saved);

        // act
        DepartmentResponseDTO res = service.createDepartment(req);

        // assert
        assertNotNull(res);
        assertEquals(1, res.getId());
        assertEquals("Entity test", res.getName());
        assertEquals("Something that somewhat matters", res.getDescription());

        // verify the payload
        ArgumentCaptor<Department> captor = ArgumentCaptor.forClass(Department.class);
        verify(repository).save(captor.capture());

        Department toSave = captor.getValue();

        assertNull(toSave.getId());
        assertEquals("Write test", toSave.getName());
        assertNotEquals("Something that really doesn't matter", toSave.getDescription());
    }

    @Test
    void getAllDepartments_returnsMappedPage() {

        // arrange
        int page = 0, pageSize = 2;

        Department dep1 = Department.builder().id(1).name("HR").description("Human Resources").build();
        Department dep2 = Department.builder().id(2).name("IT").description("Information Tech").build();

        Page<Department> departmentPage = new PageImpl<>(List.of(dep1, dep2));

        when(repository.findAll(PageRequest.of(page, pageSize))).thenReturn(departmentPage);

        // act
        Page<DepartmentResponseDTO> result = service.getAllDepartments(page, pageSize);
        DepartmentResponseDTO dto1 = result.getContent().get(0);
        DepartmentResponseDTO dto2 = result.getContent().get(1);

        // assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        // dto/dep 1
        assertEquals(dep1.getId(), dto1.getId());
        assertEquals(dep1.getName(), dto1.getName());
        assertEquals(dep1.getDescription(), dto1.getDescription());

        // dto/dep 2
        assertEquals(dep2.getId(), dto2.getId());
        assertEquals(dep2.getName(), dto2.getName());
        assertEquals(dep2.getDescription(), dto2.getDescription());

        // 1vs2
        assertNotEquals(dep1.getId(), dto2.getId());
        assertNotEquals(dep2.getName(), dto1.getName());
        assertNotEquals(dto2.getDescription(), dto1.getDescription());

    }

    @Test
    void getDepartmentById_returnsDTO() {

        // arrange
        Department entity = Department.builder().id(1).name("Finance").description("Money & stuff").build();
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        // act
        DepartmentResponseDTO dto = service.getDepartmentById(1);

        // assert
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assert (entity.getDescription().equals(dto.getDescription()));

    }

    @Test
    void getDepartmentById_throwsExceptionWhenNotFound() {

        // arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.getDepartmentById(99));

    }

    @Test
    void updateDepartmentById_updatesAndReturnsDTO() {

        // arrange
        Department existing = Department.builder().id(2).name("Old name").description("Old description").build();
        when(repository.findById(2)).thenReturn(Optional.of(existing));
        when(repository.save(any(Department.class))).thenAnswer(inv -> inv.getArgument(0));

        String oldName = existing.getName();
        String oldDesc = existing.getDescription();

        // act
        DepartmentRequestDTO req = new DepartmentRequestDTO();
        req.setName("Updated name");
        req.setDescription("Updated description");

        DepartmentResponseDTO res = service.updateDepartmentById(2, req);

        // assert
        assertEquals("Updated name", res.getName());
        assertEquals(req.getDescription(), res.getDescription());
        assertNotEquals(oldName, res.getName());
        assertNotEquals(oldDesc, res.getDescription());
        assertEquals(existing.getId(), res.getId());

    }

    @Test
    void updateDepartmentById_throwsExceptionWhenNotFound() {

        // arrange
        when(repository.findById(4)).thenReturn(Optional.empty());

        // act
        DepartmentRequestDTO req = new DepartmentRequestDTO();
        req.setName("X");
        req.setDescription("Y");

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.updateDepartmentById(4, req));

    }

    @Test
    void patchDepartmentById_updatesPartialFields() {

        // arrange
        Department existing = Department.builder().id(1).name("HR").description("People thingy").build();
        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(any(Department.class))).thenAnswer(inv -> inv.getArgument(0));

        // act: patch name
        DepartmentPatchRequestDTO patchName = new DepartmentPatchRequestDTO();
        patchName.setName("New HR");

        DepartmentResponseDTO res = service.patchDepartmentById(1, patchName);

        // assert
        assertEquals("New HR", res.getName());
        assertEquals(existing.getDescription(), res.getDescription());
        assertNull(patchName.getDescription());
        assertEquals(existing.getId(), res.getId());

        // act: patch department
        DepartmentPatchRequestDTO patchDesc = new DepartmentPatchRequestDTO();
        patchDesc.setDescription("Updated Desc");

        res = service.patchDepartmentById(1, patchDesc);

        // assert
        assertEquals("Updated Desc", res.getDescription());
        assertEquals(existing.getName(), res.getName());
        assertNull(patchDesc.getName());
        assertEquals(existing.getId(), res.getId());

        // act: for both fields
        DepartmentPatchRequestDTO patch = new DepartmentPatchRequestDTO();
        patch.setName("New Name");
        patch.setDescription("New Desc");

        res = service.patchDepartmentById(1, patch);

        // assert
        assertEquals("New Name", res.getName());
        assertEquals("New Desc", res.getDescription());
        assertEquals(existing.getId(), res.getId());

    }

    @Test
    void patchDepartmentById_throwsExceptionWhenEmptyPatch() {

        // arrange
        Department existing = Department.builder().id(1).name("Seomething").description("Something more").build();
        when(repository.findById(1)).thenReturn(Optional.of(existing));

        // act
        DepartmentPatchRequestDTO emptyPatch = new DepartmentPatchRequestDTO();

        // assert
        assertThrows(IllegalArgumentException.class, () -> service.patchDepartmentById(1, emptyPatch));

    }

    @Test
    void patchDepartmentById_throwsExceptionWhenNotFound() {

        // arrange
        when(repository.findById(4)).thenReturn(Optional.empty());

        // act
        DepartmentPatchRequestDTO patch = new DepartmentPatchRequestDTO();
        patch.setDescription("Updated description");

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.patchDepartmentById(4, patch));

    }

    @Test
    void deleteDepartmentById_deletesSuccessfully() {

        // arrange
        Department existing = Department.builder().id(1).name("IT").build();
        when(repository.findById(1)).thenReturn(Optional.of(existing));

        // act
        service.deleteDepartmentById(1);

        // assert
        verify(repository).delete(existing);
    }

    @Test
    void deleteDepartmentById_throwsWhenNotFound() {

        // arrange
        when(repository.findById(50)).thenReturn(Optional.empty());

        // assert
        assertThrows(EntityNotFoundException.class, () -> service.deleteDepartmentById(50));
    }

}
