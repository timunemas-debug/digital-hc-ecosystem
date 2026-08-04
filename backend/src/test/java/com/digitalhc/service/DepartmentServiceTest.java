package com.digitalhc.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitalhc.DTO.request.DepartmentRequest;
import com.digitalhc.DTO.response.DepartmentResponse;
import com.digitalhc.mapper.DepartmentMapper;
import com.digitalhc.model.Department;
import com.digitalhc.model.DepartmentName;
import com.digitalhc.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    
    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    DepartmentMapper departmentMapper;

    @InjectMocks
    DepartmentService departmentService;

    Long departmentId = 1L;

    @Test
    public void shouldAddDepartment(){

        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(DepartmentName.EMPLOYEE);
        department.setDescription("Test");

        DepartmentRequest request = new DepartmentRequest();
        request.setDepartementName(DepartmentName.EMPLOYEE);
        request.setDescription("Test");

        DepartmentResponse response = new DepartmentResponse();
        response.setDepartementName(DepartmentName.EMPLOYEE);
        response.setDescription("Test");

        when(departmentRepository.existsByDepartmentName(DepartmentName.EMPLOYEE))
                .thenReturn(false);

        when(departmentMapper.toEntity(request))
                .thenReturn(department);

        when(departmentMapper.toResponse(department))
                .thenReturn(response);

        when(departmentRepository.save(department))
                .thenReturn(department);

        DepartmentResponse result = departmentService.addDepartment(request);

        assertEquals(DepartmentName.EMPLOYEE, result.getDepartementName());
        assertEquals("Test", result.getDescription());

        verify(departmentRepository).existsByDepartmentName(DepartmentName.EMPLOYEE);
        verify(departmentMapper).toEntity(request);
        verify(departmentMapper).toResponse(department);
        verify(departmentRepository).save(department);
    }

    @Test
    public void shouldGetAllDepartmetn(){

        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(DepartmentName.ADMIN);
        department.setDescription("Test");

        Department department2 = new Department();
        department2.setDepartmentId(2L);
        department2.setDepartmentName(DepartmentName.EMPLOYEE);
        department2.setDescription("Test");

        DepartmentResponse response1 = new DepartmentResponse();
        response1.setDepartementName(DepartmentName.ADMIN);
        response1.setDescription("Test");

        DepartmentResponse response2 = new DepartmentResponse();
        response2.setDepartementName(DepartmentName.EMPLOYEE);
        response2.setDescription("Test");

        when(departmentRepository.findAll())
                .thenReturn(List.of(department, department2));

        when(departmentMapper.toResponse(department))
                .thenReturn(response1);

        when(departmentMapper.toResponse(department2))
                .thenReturn(response2);

        List<DepartmentResponse> result = departmentService.getAllDepartment();

        assertEquals(2, result.size());
        assertEquals(DepartmentName.ADMIN, result.get(0).getDepartementName());
        assertEquals(DepartmentName.EMPLOYEE, result.get(1).getDepartementName());

        verify(departmentRepository).findAll();
        verify(departmentMapper).toResponse(department);
        verify(departmentMapper).toResponse(department2);
    }

    @Test
    public void shouldGetDepartmentById(){

        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(DepartmentName.EMPLOYEE);
        department.setDescription("Test");

        DepartmentResponse response = new DepartmentResponse();
        response.setDepartementName(DepartmentName.EMPLOYEE);
        response.setDescription("Test");

        when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.of(department));

        when(departmentMapper.toResponse(department))
                .thenReturn(response);

        DepartmentResponse result = departmentService.getDepartmentById(departmentId);

        assertEquals(DepartmentName.EMPLOYEE, result.getDepartementName());
        assertEquals("Test", result.getDescription());

        verify(departmentRepository).findById(departmentId);
        verify(departmentMapper).toResponse(department);
    }

    @Test
    public void shouldUpdateDepartment(){

        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(DepartmentName.ADMIN);

        DepartmentRequest request = new DepartmentRequest();
        request.setDepartementName(DepartmentName.EMPLOYEE);

        DepartmentResponse response = new DepartmentResponse();
        response.setDepartementName(DepartmentName.EMPLOYEE);

        when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.of(department));

        when(departmentRepository.existsByDepartmentName(DepartmentName.EMPLOYEE))
                .thenReturn(false);

        when(departmentMapper.toResponse(department))
                .thenReturn(response);

        when(departmentRepository.save(department))
                .thenReturn(department);

        DepartmentResponse result = departmentService.updateDepartment(departmentId, request);

        assertEquals(DepartmentName.EMPLOYEE, result.getDepartementName());

        verify(departmentRepository).findById(departmentId);
        verify(departmentRepository).existsByDepartmentName(DepartmentName.EMPLOYEE);
        verify(departmentMapper).toResponse(department);
        verify(departmentRepository).save(department);
    }

    @Test
    public void shouldDeleteDepartment(){

        when(departmentRepository.existsById(departmentId))
                .thenReturn(true);

        departmentService.deleteDepartment(departmentId);

        verify(departmentRepository).existsById(departmentId);
        verify(departmentRepository).deleteById(departmentId);
    }
}