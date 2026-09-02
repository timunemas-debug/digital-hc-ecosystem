package com.digitalhc.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.AttendanceRequest;
import com.digitalhc.DTO.response.AttendanceResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.AttendanceMapper;
import com.digitalhc.model.Attendance;
import com.digitalhc.model.AttendanceStatus;
import com.digitalhc.model.Employee;
import com.digitalhc.repository.AttendanceRepository;
import com.digitalhc.repository.EmployeeRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceService {
       
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final EmployeeRepository employeeRepository;
    private static final ZoneId ZONE_JAKARTA = ZoneId.of("Asia/Jakarta");

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper, EmployeeRepository employeeRepository){
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.employeeRepository = employeeRepository;
    }

    private Employee getEmployeeById(Long employeeId){

        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFound("Employee tidak ditemukan!"));
    }

    private Attendance getAttendanceById(Long attendanceId){
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFound("Attendance tidak ditemukan!"));
    }

    @Transactional
    public AttendanceResponse checkIn(AttendanceRequest request){
        
        Employee employee = getEmployeeById(request.getEmployeeId());
        LocalDate today = LocalDate.now(ZONE_JAKARTA);
        LocalDateTime now = LocalDateTime.now(ZONE_JAKARTA);

        if (attendanceRepository.findByEmployeeEmployeeIdAndAttendanceDate(employee.getEmployeeId(), today).isPresent()) {
            throw new BadRequestException("Employee sudah melakukan check in hari ini");
        }

        Attendance attendance = attendanceMapper.toEntity(request);
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(today);
        attendance.setCheckIn(now);
        attendance.setAttendanceStatus(
            now.toLocalTime().isAfter(LocalTime.of(9, 15)) ? AttendanceStatus.TELAT : AttendanceStatus.HADIR
        );
        
        try {
            return attendanceMapper.toResponse(attendanceRepository.save(attendance));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Employee sudah melakukan check in hari ini!");
        }
    }

    @Transactional
    public void checkOut(Long employeeId){

        LocalDate today = LocalDate.now(ZONE_JAKARTA);

        getEmployeeById(employeeId);

        Attendance attendance = attendanceRepository.findByEmployeeEmployeeIdAndAttendanceDate(employeeId, today)
                .orElseThrow(() -> new ResourceNotFound("Employee belum melakukan check in hari ini!"));

        if (attendance.getCheckOut() != null) {
            throw new BadRequestException("Employee sudah melakukan check out");
        }

        attendance.setCheckOut(LocalDateTime.now(ZONE_JAKARTA));

        attendanceRepository.save(attendance);
    }

    public AttendanceResponse getAttendanceResponseById(Long attendanceId){

        Attendance attendance = getAttendanceById(attendanceId);

        return attendanceMapper.toResponse(attendance);
    }

    public List<AttendanceResponse> getAllAttendance(){

        return attendanceRepository.findAll()
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    public List<AttendanceResponse> getAttendanceByEmployee(Long employeeId){

        getEmployeeById(employeeId);

        return attendanceRepository.findByEmployeeEmployeeId(employeeId)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    public List<AttendanceResponse> getAttendanceByDate(LocalDate date){

        return attendanceRepository.findByAttendanceDate(date)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    public AttendanceResponse getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date){

        Attendance attendance = attendanceRepository.findByEmployeeEmployeeIdAndAttendanceDate(employeeId, date)
                    .orElseThrow(() -> new ResourceNotFound("Attendance tidak ditemukan"));

        return attendanceMapper.toResponse(attendance);
    }
    
    public List<AttendanceResponse> getAttendanceByStatus(AttendanceStatus status, LocalDate date){

        List<Attendance> attendance = attendanceRepository.findByAttandanceStatusAndAttendanceDate(status, date);
        
        if (attendance.isEmpty()) {
            throw new ResourceNotFound("Tidak ada employee dengan status" + status);
        }

        return attendance.stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }
}