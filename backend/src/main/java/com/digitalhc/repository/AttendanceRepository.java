package com.digitalhc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    
    Optional<Attendance> findByEmployeeEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);

    List<Attendance> findByEmployeeEmployeeId(Long employeeId);

    List<Attendance> findByAttendanceDate(LocalDate date);
}