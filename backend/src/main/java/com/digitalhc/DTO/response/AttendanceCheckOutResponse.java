package com.digitalhc.DTO.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCheckOutResponse {
    
    private String message;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}