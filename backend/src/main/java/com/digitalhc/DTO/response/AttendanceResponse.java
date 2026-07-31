package com.digitalhc.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    
    private String attendanceDate;
    private double checkIn;
    private double checkOut;
    private double lateMinutes;
}