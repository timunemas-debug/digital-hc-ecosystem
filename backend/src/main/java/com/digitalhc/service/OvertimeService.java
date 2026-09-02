package com.digitalhc.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalhc.DTO.request.OvertimeRequest;
import com.digitalhc.DTO.response.OvertimeResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.OvertimeMapper;
import com.digitalhc.model.Attendance;
import com.digitalhc.model.Employee;
import com.digitalhc.model.OverTimeStatus;
import com.digitalhc.model.Overtime;
import com.digitalhc.repository.AttendanceRepository;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.repository.OvertimeRepository;

@Service
public class OvertimeService {
    
    private final OvertimeRepository overtimeRepository;
    private final OvertimeMapper overtimeMapper;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public OvertimeService(OvertimeRepository overtimeRepository, OvertimeMapper overtimeMapper, AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository){
        this.overtimeRepository = overtimeRepository;
        this.overtimeMapper = overtimeMapper;
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public OvertimeResponse addOvertime(Long employeeId, OvertimeRequest request){

        Employee employee = employeeRepository.findByEmployeeIdWithLock(employeeId)
                .orElseThrow(() -> new ResourceNotFound("Employee tidak ditemukan!"));

        Optional<Attendance> attendanceEmployee = attendanceRepository.findByEmployeeEmployeeIdAndAttendanceDate(employeeId, request.getDate());

        if (attendanceEmployee.isEmpty()) {
            throw new BadRequestException("Employee tidak memiliki attendance pada tanggal tersebut!");
        }

        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new BadRequestException("Start time harus sebelum end time!");
        }

        if (overtimeRepository.existsByEmployeeEmployeeIdAndDate(employeeId, request.getDate())) {
            throw new BadRequestException("Employe sudah memiliki overtime pada tanggal tersebut!");
        }

        Overtime overtime = overtimeMapper.toEntity(request);

        overtime.setEmployee(employee);
        overtime.setStatus(OverTimeStatus.SUBMITTED);

        try {
            return overtimeMapper.toMapResponse(overtimeRepository.save(overtime));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Employee sudah melakukan overtime hari ini!");
        }
    }
}