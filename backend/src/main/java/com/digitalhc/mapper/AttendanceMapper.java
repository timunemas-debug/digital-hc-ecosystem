package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.AttendanceRequest;
import com.digitalhc.DTO.response.AttendanceResponse;
import com.digitalhc.model.Attendance;

@Component
public class AttendanceMapper {
    
    public Attendance toEntity(AttendanceRequest request){

        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setCheckIn(request.getCheckIn());
        attendance.setCheckOut(request.getCheckOut());
        attendance.setLateMinutes(request.getLateMinutes());

        return attendance;
    }

    public AttendanceResponse toResponse(Attendance attendance){
        return new AttendanceResponse(attendance.getAttendanceDate(),
                                      attendance.getCheckIn(),
                                      attendance.getCheckOut(),
                                      attendance.getLateMinutes());
    }
}