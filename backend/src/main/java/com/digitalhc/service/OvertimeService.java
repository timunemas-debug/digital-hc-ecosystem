package com.digitalhc.service;

import java.time.LocalDateTime;
import java.util.List;
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
import com.digitalhc.model.Role;
import com.digitalhc.model.User;
import com.digitalhc.repository.AttendanceRepository;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.repository.OvertimeRepository;
import com.digitalhc.repository.UserRepository;
import com.digitalhc.security.CustomUserDetails;
import com.digitalhc.security.SecurityService;

@Service
public class OvertimeService {
    
    private final OvertimeRepository overtimeRepository;
    private final OvertimeMapper overtimeMapper;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public OvertimeService(OvertimeRepository overtimeRepository, OvertimeMapper overtimeMapper, AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository, SecurityService securityService, UserRepository userRepository){
        this.overtimeRepository = overtimeRepository;
        this.overtimeMapper = overtimeMapper;
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    @Transactional
    public OvertimeResponse addOvertime(OvertimeRequest request){

        Long employeeId = securityService.getCurrentUserId();

        Employee employee = employeeRepository.findByEmployeeIdWithLock(employeeId)
                .orElseThrow(() -> new ResourceNotFound("Employee tidak ditemukan!"));

        Optional<Attendance> attendanceEmployee = attendanceRepository.findByEmployeeEmployeeIdAndAttendanceDate(employeeId, request.getDate());

        if (attendanceEmployee.isEmpty()) {
            throw new BadRequestException("Employee tidak memiliki attendance pada tanggal tersebut!");
        }

        if (!request.getStartTime().toLocalDate().equals(request.getDate()) || !request.getEndTime().toLocalDate().equals(request.getDate())) {
            throw new BadRequestException("Tanggal overtime tidak sama dengan start time dan end time!");
        }

        if (!request.getStartTime().isBefore(request.getEndTime())) {
            throw new BadRequestException("Start time harus sebelum end time!");
        }

        if (overtimeRepository.existsByEmployeeEmployeeIdAndDate(employeeId, request.getDate())) {
            throw new BadRequestException("Employee sudah memiliki overtime pada tanggal tersebut!");
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

    public List<OvertimeResponse> getAllOvertime(){
        return overtimeRepository.findAll()
            .stream()
            .map(overtimeMapper::toMapResponse)
            .toList();
    }

    public OvertimeResponse getOvertimeById(Long overtimeId){

        Overtime overtime = overtimeRepository.findById(overtimeId)
                .orElseThrow(() -> new ResourceNotFound("Overtime dengan id tersebut tidak ditemukan!"));

        return overtimeMapper.toMapResponse(overtime);
    }

    @Transactional
    public void approvedOvertime(Long overtimeId, OverTimeStatus status){

        Overtime overtime = overtimeRepository.findByOvertimeIdWithLock(overtimeId)
                .orElseThrow(() -> new ResourceNotFound("Overtime tidak ditemukan!"));

        if (overtime.getStatus() != OverTimeStatus.SUBMITTED) {
            throw new BadRequestException("Status overtime sudah di proses dan tidak dapat diubah!");
        }

        if (status != OverTimeStatus.APPROVED && status != OverTimeStatus.REJECTED) {
            throw new BadRequestException("Status hanya dapat menjadi approved atau rejected");
        }

        if (overtime.getApprovedBy() != null) {
            throw new BadRequestException("Overtime sudah di proses!");
        }

        Long userId = securityService.getCurrentUserId();

        User currentUser = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFound("User dengan id tersebut tidak ditemukan!"));

        overtime.setApprovedBy(currentUser);
        overtime.setStatus(status);
        overtime.setApprovedAt(LocalDateTime.now());

        overtimeRepository.save(overtime);
    }
}