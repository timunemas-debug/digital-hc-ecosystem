package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.response.AttendanceCheckOutResponse;
import com.digitalhc.DTO.response.AttendanceResponse;
import com.digitalhc.model.Attendance;

@Component
public class AttendanceMapper {
    
    public AttendanceResponse toResponse(Attendance attendance){
        return new AttendanceResponse("Anda berhasil checkIn!",
                                      attendance.getAttendanceDate(),
                                      attendance.getCheckIn(),
                                      attendance.getAttendanceStatus(),
                                      attendance.getLateMinutes());
    }

    public AttendanceCheckOutResponse toResponseCheckOut(Attendance attendance){
        return new AttendanceCheckOutResponse("Anda berhasil checkOut!",
                                              attendance.getCheckOut(),
                                              attendance.getCheckOut());
    }
}