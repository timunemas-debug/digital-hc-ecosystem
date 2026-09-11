package com.digitalhc.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.response.AttendanceResponse;
import com.digitalhc.service.AttendanceService;


@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService){
        this.attendanceService = attendanceService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/checkIn")
    public AttendanceResponse checkIn(){
        return attendanceService.checkIn();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/checkOut")
    public void checkOut(){
        attendanceService.checkOut();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/attendance-employee/{employeeId}")
    public List<AttendanceResponse> getAttendanceByEmployeeId(@PathVariable Long employeeId){
        return attendanceService.getAttendanceByEmployee(employeeId);
    }
}