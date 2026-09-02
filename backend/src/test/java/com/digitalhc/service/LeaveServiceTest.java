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

import com.digitalhc.DTO.request.LeaveRequest;
import com.digitalhc.DTO.response.LeaveResponse;
import com.digitalhc.mapper.LeaveMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.Leave;
import com.digitalhc.model.LeaveStatus;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.repository.LeaveRepository;

@ExtendWith(MockitoExtension.class)
public class LeaveServiceTest {
    
    @Mock
    LeaveRepository leaveRepository;

    @Mock
    LeaveMapper leaveMapper;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    LeaveService leaveService;

    @Test
    public void shouldAddLeave(){

        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setNamaLengkapEmployee("Jeremy");
        employee.setTanggalBergabungEmployee(LocalDate.of(2, 4, 12));

        Leave leave = new Leave();
        leave.setReasonLeave("Test");
        leave.setStartDateLeave(LocalDate.of(2026, 7, 5));
        leave.setStatus(LeaveStatus.SUBMITTED);

        LeaveRequest request = new LeaveRequest();
        request.setReasonLeave("Test");
        request.setStartDateLeave(LocalDate.of(2026, 7, 5));

        LeaveResponse response = new LeaveResponse();
        response.setReasonLeave("Test");
        response.setStartDateLeave(LocalDate.of(2026, 7, 5));
        response.setStatus(LeaveStatus.SUBMITTED);

        when(employeeRepository.findByEmployeeIdWithLock(1L))
                .thenReturn(Optional.of(employee));

        when(leaveRepository.countByEmployeeAndStatus(employee, LeaveStatus.SUBMITTED))
                .thenReturn(1L);

        when(leaveMapper.toEntity(request))
                .thenReturn(leave);

        when(leaveMapper.toResponse(leave))
                .thenReturn(response);

        when(leaveRepository.save(leave))
                .thenReturn(leave);

        LeaveResponse result = leaveService.addLeave(1L, request);

        assertEquals("Test", result.getReasonLeave());
        assertEquals(LocalDate.of(2026, 7, 5), result.getStartDateLeave());

        verify(leaveRepository).countByEmployeeAndStatus(employee, LeaveStatus.SUBMITTED);
        verify(leaveMapper).toEntity(request);
        verify(leaveMapper).toResponse(leave);
        verify(leaveRepository).save(leave);
    }

    @Test
    public void shouldGetAllLeave(){

        Leave leave = new Leave();
        leave.setReasonLeave("Test");

        Leave leave2 = new Leave();
        leave2.setReasonLeave("Test2");

        LeaveResponse response1 = new LeaveResponse();
        response1.setReasonLeave("Test");

        LeaveResponse response2 = new LeaveResponse();
        response2.setReasonLeave("Test2");

        when(leaveRepository.findAll())
                .thenReturn(List.of(leave, leave2));

        when(leaveMapper.toResponse(leave))
                .thenReturn(response1);

        when(leaveMapper.toResponse(leave2))
                .thenReturn(response2);

        List<LeaveResponse> result = leaveService.getAllLeave();

        assertEquals("Test", result.get(0).getReasonLeave());
        assertEquals("Test2", result.get(1).getReasonLeave());

        verify(leaveRepository).findAll();
        verify(leaveMapper).toResponse(leave);
        verify(leaveMapper).toResponse(leave2);
    }

    @Test
    public void shouldGetLeaveByLeaveId(){

        Leave leave = new Leave();
        leave.setLeaveId(1L);
        leave.setReasonLeave("test");

        when(leaveRepository.findById(1L))
                .thenReturn(Optional.of(leave));

        Leave result = leaveService.getLeaveByLeaveId(1L);

        assertEquals("test", result.getReasonLeave());

        verify(leaveRepository).findById(1L);
    }

    @Test
    public void shouldGetLeaveResponseByEmployeeId(){

        Leave leave = new Leave();
        leave.setLeaveId(1L);
        leave.setReasonLeave("test");

        LeaveResponse response = new LeaveResponse();
        response.setReasonLeave("test");

        when(leaveRepository.findByEmployeeEmployeeId(1L))
                .thenReturn(List.of(leave));

        when(leaveMapper.toResponse(leave))
                .thenReturn(response);

        List<LeaveResponse> result = leaveService.getLeaveResponseByEmployeeId(1L);

        assertEquals("test", result.get(0).getReasonLeave());

        verify(leaveRepository).findByEmployeeEmployeeId(1L);
        verify(leaveMapper).toResponse(leave);
    }
}