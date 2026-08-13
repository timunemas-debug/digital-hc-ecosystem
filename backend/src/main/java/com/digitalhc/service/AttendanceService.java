package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.AttendanceRequest;
import com.digitalhc.DTO.response.AttendanceResponse;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.AttendanceMapper;
import com.digitalhc.model.Attendance;
import com.digitalhc.model.Employee;
import com.digitalhc.repository.AttendanceRepository;
import com.digitalhc.repository.EmployeeRepository;

@Service
public class AttendanceService {
       
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper, EmployeeRepository employeeRepository){
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.employeeRepository = employeeRepository;
    }

    private Employee getEmployeeById(Long employeeId){

        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFound("Employee tidak ditemukan!"));
    }

    public AttendanceResponse addAttendance(AttendanceRequest request){

        Employee employee = getEmployeeById(request.getEmployeeId());

        Attendance attendance = attendanceMapper.toEntity(request);
        attendance.setEmployee(employee);

        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }
}