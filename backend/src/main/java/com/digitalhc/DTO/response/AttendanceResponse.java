package com.digitalhc.DTO.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.digitalhc.model.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    
    private String message;
    private LocalDate attendanceDate;
    private LocalDateTime checkIn;
    private AttendanceStatus status;
    private Long lateMinutes;
}