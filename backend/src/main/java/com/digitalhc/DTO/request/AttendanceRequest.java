package com.digitalhc.DTO.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRequest {
    
    @NotNull(message = "Employee id wajib di isi!")
    private Long employeeId;

    @NotNull(message = "Tanggal wajib di isi!")
    private LocalDate attendanceDate;

    @NotNull(message = "Check in wajib di isi!")
    private LocalDateTime checkIn;

    @NotNull(message = "Check out wajib di isi!")
    private LocalDateTime checkOut;

    @NotNull(message = "Late minutes wajib di isi!")
    private LocalDateTime lateMinutes;
}