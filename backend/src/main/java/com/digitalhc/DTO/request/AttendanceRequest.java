package com.digitalhc.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRequest {
    
    @NotBlank(message = "Tanggal wajib di isi!")
    private String attendanceDate;

    @NotNull(message = "Check in wajib di isi!")
    private double checkIn;

    @NotNull(message = "Check out wajib di isi!")
    private double checkOut;

    @NotNull(message = "Late minutes wajib di isi!")
    private double lateMinutes;
}