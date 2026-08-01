package com.digitalhc.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitalhc.DTO.request.EmployeeRequest;
import com.digitalhc.DTO.response.EmployeeResponse;
import com.digitalhc.mapper.EmployeeMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    
    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    EmployeeMapper employeeMapper;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    public void shouldAddEmployee(){

        Employee employee = new Employee();
        employee.setNik(1L);
        employee.setNamaLengkapEmployee("Jeremy");
        employee.setNomerHpEmployee(012345L);
        employee.setTanggalLahirEmployee(LocalDate.of(2004, 1, 18));
        employee.setTanggalBergabungEmployee(LocalDate.of(2026,8,1));
        employee.setCreateAt(LocalDate.of(2026,8,1));
        employee.setUpdateAt(LocalDate.of(2027, 8, 1));

        EmployeeRequest request = new EmployeeRequest();
        request.setNik(1L);
        request.setNamaLengkapEmployee("Jeremy");
        request.setNomerHpEmployee(012345L);
        request.setTanggalLahirEmployee(LocalDate.of(2004, 1, 18));
        request.setTanggalBergabungEmployee(LocalDate.of(2026,8,1));
        request.setCreateAt(LocalDate.of(2026,8,1));
        request.setUpdateAt(LocalDate.of(2027, 8, 1));

        EmployeeResponse response = new EmployeeResponse();
        response.setNik(1L);
        response.setNamaLengkapEmployee("Jeremy");
        response.setNomerHpEmployee(012345L);
        response.setTanggalLahirEmployee(LocalDate.of(2004, 1, 18));
        response.setTanggalBergabungEmployee(LocalDate.of(2026,8,1));
        response.setCreateAt(LocalDate.of(2026,8,1));
        response.setUpdateAt(LocalDate.of(2027, 8, 1));

        when(employeeRepository.existsByNamaLengkapEmployee("Jeremy"))
                .thenReturn(false);

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(employeeMapper.toEntity(request))
                .thenReturn(employee);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse result = employeeService.addEmployee(request);

        assertEquals("Jeremy", result.getNamaLengkapEmployee());
        assertEquals(012345L, result.getNomerHpEmployee());

        verify(employeeRepository).existsByNamaLengkapEmployee("Jeremy");
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toEntity(request);
        verify(employeeMapper).toResponse(employee);
    }
}
