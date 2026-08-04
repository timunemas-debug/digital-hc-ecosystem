package com.digitalhc.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitalhc.DTO.request.EmployeeRequest;
import com.digitalhc.DTO.request.UpdateEmployeeRequest;
import com.digitalhc.DTO.response.EmployeeResponse;
import com.digitalhc.DTO.response.UpdateEmployeeResponse;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.EmployeeMapper;
import com.digitalhc.mapper.UpdateEmployeeMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.EmployeeStatus;
import com.digitalhc.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    
    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    EmployeeMapper employeeMapper;
    
    @Mock
    UpdateEmployeeMapper updateEmployeeMapper;

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

        EmployeeResponse response = new EmployeeResponse();
        response.setNik(1L);
        response.setNamaLengkapEmployee("Jeremy");
        response.setNomerHpEmployee(012345L);
        response.setTanggalLahirEmployee(LocalDate.of(2004, 1, 18));
        response.setTanggalBergabungEmployee(LocalDate.of(2026,8,1));

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

    @Test
    public void shouldGetEmployeeById(){
        
        Employee employee = new Employee();
        employee.setEmployeeId(2L);
        employee.setNik(1L);
        employee.setNamaLengkapEmployee("Jeremy");
        employee.setNomerHpEmployee(012345L);
        employee.setTanggalLahirEmployee(LocalDate.of(2004, 1, 18));
        employee.setTanggalBergabungEmployee(LocalDate.of(2026,8,1));
        employee.setCreateAt(LocalDate.of(2026,8,1));
        employee.setUpdateAt(LocalDate.of(2027, 8, 1));

        when(employeeRepository.findById(2L))
                .thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(2L);

        assertEquals(2L, result.getEmployeeId());

        verify(employeeRepository).findById(2L);
    }

    @Test
    public void shouldGetEmployeeResponseById(){

        Employee employee = new Employee();
        employee.setEmployeeId(2L);
        employee.setNik(1L);
        employee.setNamaLengkapEmployee("Jeremy");
        employee.setNomerHpEmployee(012345L);
        employee.setTanggalLahirEmployee(LocalDate.of(2004, 1, 18));
        employee.setTanggalBergabungEmployee(LocalDate.of(2026,8,1));
        employee.setCreateAt(LocalDate.of(2026,8,1));
        employee.setUpdateAt(LocalDate.of(2027, 8, 1));

        EmployeeResponse response = new EmployeeResponse();
        response.setNik(1L);
        response.setNamaLengkapEmployee("Jeremy");
        response.setNomerHpEmployee(012345L);
        response.setTanggalLahirEmployee(LocalDate.of(2004, 1, 18));
        response.setTanggalBergabungEmployee(LocalDate.of(2026,8,1));

        when(employeeRepository.findById(2L))
                .thenReturn(Optional.of(employee));

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse result = employeeService.getEmployeeResponseById(2L);

        assertEquals(1L, result.getNik());
        assertEquals("Jeremy", result.getNamaLengkapEmployee());

        verify(employeeRepository).findById(2L);
        verify(employeeMapper).toResponse(employee);
    }

    @Test
    public void shouldGetEmployeeByNama(){

        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setNamaLengkapEmployee("Jeremy");

        EmployeeResponse response = new EmployeeResponse();
        response.setNik(1L);
        response.setNamaLengkapEmployee("Jeremy");

        when(employeeRepository.findByNamaLengkapEmployee("Jeremy"))
                .thenReturn(Optional.of(employee));

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        EmployeeResponse result = employeeService.getEmployeeByNama("Jeremy");
        
        assertEquals(1L, result.getNik());
        assertEquals("Jeremy", result.getNamaLengkapEmployee());

        verify(employeeRepository).findByNamaLengkapEmployee("Jeremy");
        verify(employeeMapper).toResponse(employee);
    }

    @Test
    public void shouldGetEmployeeByTanggalBergabungSetelah(){

        LocalDate tanggal = LocalDate.of(2026, 1, 18);
        
        Employee employee = new Employee();
        employee.setNik(1L);
        employee.setNamaLengkapEmployee("Jeremy");

        EmployeeResponse response1 = new EmployeeResponse();
        response1.setNik(1L);
        response1.setNamaLengkapEmployee("Jeremy");

        when(employeeRepository.findByTanggalBergabungEmployee(tanggal))
                .thenReturn(List.of(employee));

        when(employeeMapper.toResponse(employee))
                .thenReturn(response1);

        List<EmployeeResponse> result = employeeService.getEmployeeByTanggalBergabungSetelah(tanggal);

        assertEquals(1, result.size());
        assertEquals("Jeremy", result.get(0).getNamaLengkapEmployee());

        verify(employeeRepository).findByTanggalBergabungEmployee(tanggal);
        verify(employeeMapper).toResponse(employee);
    }

    @Test
    public void shouldGetEmployeeTanggalBergabungBetween(){
        LocalDate tanggal1 = LocalDate.of(2026, 1, 18);
        LocalDate tanggal2 = LocalDate.of(2024, 1, 18);

        Employee employee1 = new Employee();
        employee1.setNik(1L);
        employee1.setNamaLengkapEmployee("Jeremy");

        EmployeeResponse response1 = new EmployeeResponse();
        response1.setNik(1L);
        response1.setNamaLengkapEmployee("Jeremy");

        Employee employee2 = new Employee();
        employee2.setNik(2L);
        employee2.setNamaLengkapEmployee("Pretty");

        EmployeeResponse response2 = new EmployeeResponse();
        response2.setNik(2L);
        response2.setNamaLengkapEmployee("Pretty");

        when(employeeRepository.findByTanggalBergabungEmployeeBetween(tanggal1, tanggal2))
                .thenReturn(List.of(employee1, employee2));

        when(employeeMapper.toResponse(employee1))
                .thenReturn(response1);

        when(employeeMapper.toResponse(employee2))
                .thenReturn(response2);

        List<EmployeeResponse> resuList = employeeService.getEmployeeByTanggalBergabungBetweeen(tanggal1, tanggal2);

        assertEquals(2, resuList.size());
        assertEquals(1L, resuList.get(0).getNik());
        assertEquals("Jeremy", resuList.get(0).getNamaLengkapEmployee());
        assertEquals(2L, resuList.get(1).getNik());
        assertEquals("Pretty", resuList.get(1).getNamaLengkapEmployee());

        verify(employeeRepository).findByTanggalBergabungEmployeeBetween(tanggal1, tanggal2);
        verify(employeeMapper).toResponse(employee1);
        verify(employeeMapper).toResponse(employee2);
    }

    @Test
    public void shouldGetEmployeeStatus(){

        Employee employee = new Employee();
        employee.setNik(1L);
        employee.setNamaLengkapEmployee("jeremy");
        employee.setStatus(EmployeeStatus.AKTIF);

        EmployeeResponse response = new EmployeeResponse();
        response.setNik(1L);
        response.setNamaLengkapEmployee("jeremy");

        when(employeeRepository.findByStatus(EmployeeStatus.AKTIF))
                .thenReturn(List.of(employee));

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        List<EmployeeResponse> result = employeeService.getEmployeeStatus(EmployeeStatus.AKTIF);

        assertEquals(1L, result.get(0).getNik());
        assertEquals("jeremy", result.get(0).getNamaLengkapEmployee());

        verify(employeeRepository).findByStatus(EmployeeStatus.AKTIF);
        verify(employeeMapper).toResponse(employee);
    }

    @Test
    public void shouldDeleteEmployeeById(){
        
        when(employeeRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(ResourceNotFound.class, () -> employeeService.deleteEmployeeById(1L));
        
        verify(employeeRepository).existsById(1L);
        verify(employeeRepository, never()).deleteById(any());
    }

    @Test
    public void shouldUpdatePorfileEmployee(){
        
        Employee employee = new Employee();
        employee.setNik(1L);
        employee.setNamaLengkapEmployee("Jeremy");

        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setNik(2L);
        request.setNamaLengkapEmployee("Pretty");

        UpdateEmployeeResponse response = new UpdateEmployeeResponse();
        response.setNik(2L);
        response.setNamaLengkapEmployee("Pretty");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(updateEmployeeMapper.mapToResponse(employee))
                .thenReturn(response);

        UpdateEmployeeResponse result = employeeService.updateProfileEmployee(1L, request);

        assertEquals(2L, result.getNik());
        assertEquals("Pretty", result.getNamaLengkapEmployee());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(employee);
        verify(updateEmployeeMapper).mapToResponse(employee);
    }
}