package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    
}