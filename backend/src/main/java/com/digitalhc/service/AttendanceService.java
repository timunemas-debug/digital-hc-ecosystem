package com.digitalhc.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.digitalhc.DTO.response.AttendanceCheckOutResponse;
import com.digitalhc.DTO.response.AttendanceResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.AttendanceMapper;
import com.digitalhc.model.Attendance;
import com.digitalhc.model.AttendanceStatus;
import com.digitalhc.model.Employee;
import com.digitalhc.repository.AttendanceRepository;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.security.SecurityService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceService {
       
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final EmployeeRepository employeeRepository;
    private final SecurityService securityService;
    private static final ZoneId ZONE_JAKARTA = ZoneId.of("Asia/Jakarta");

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper, EmployeeRepository employeeRepository, SecurityService securityService){
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.employeeRepository = employeeRepository;
        this.securityService = securityService;
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
    public AttendanceResponse checkIn(){
        
        Long employeeId = securityService.getCurrentUserId();
        Employee employee = getEmployeeById(employeeId);
        LocalDate today = LocalDate.now(ZONE_JAKARTA);
        LocalDateTime now = LocalDateTime.now(ZONE_JAKARTA);
        LocalTime batasMasuk = LocalTime.of(9, 15);
        LocalTime checkIn = now.toLocalTime();
        Long lateMinutes = 0L;

        if (attendanceRepository.findByEmployeeEmployeeIdAndAttendanceDate(employee.getEmployeeId(), today).isPresent()) {
            throw new BadRequestException("Employee sudah melakukan check in hari ini");
        }

        if (checkIn.isAfter(batasMasuk)) {
            lateMinutes = Duration.between(batasMasuk, checkIn).toMinutes();
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(today);
        attendance.setCheckIn(now);
        attendance.setLateMinutes(lateMinutes);
        attendance.setAttendanceStatus(
            checkIn.isAfter(batasMasuk) ? AttendanceStatus.TELAT : AttendanceStatus.HADIR
        );
        
        try {
            return attendanceMapper.toResponse(attendanceRepository.save(attendance));
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Employee sudah melakukan check in hari ini!");
        }
    }

    @Transactional
    public AttendanceCheckOutResponse checkOut(){

        LocalDate today = LocalDate.now(ZONE_JAKARTA);
        LocalTime batasKerja = LocalTime.of(16, 15);
        LocalDateTime now = LocalDateTime.now(ZONE_JAKARTA);
        LocalTime checkOut = now.toLocalTime();

        Long employeeId = securityService.getCurrentUserId();
        getEmployeeById(employeeId);

        Attendance attendance = attendanceRepository.findByEmployeeEmployeeIdAndAttendanceDate(employeeId, today)
                .orElseThrow(() -> new ResourceNotFound("Employee belum melakukan check in hari ini!"));

        if (attendance.getCheckOut() != null) {
            throw new BadRequestException("Employee sudah melakukan check out");
        }

        if (checkOut.isBefore(batasKerja)) {
            throw new BadRequestException("Anda tidak bisa melakukan checkOut!");
        }

        attendance.setCheckOut(LocalDateTime.now(ZONE_JAKARTA));

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return attendanceMapper.toResponseCheckOut(savedAttendance);
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

        List<Attendance> attendance = attendanceRepository.findByAttendanceStatusAndAttendanceDate(status, date);
        
        if (attendance.isEmpty()) {
            throw new ResourceNotFound("Tidak ada employee dengan status" + status);
        }

        return attendance.stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }
}