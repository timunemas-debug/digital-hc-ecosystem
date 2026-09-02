package com.digitalhc.DTO.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OvertimeRequest {

    @NotNull(message = "Date wajib di isi!")
    private LocalDate date;

    @NotNull(message = "Start time wajib di isi!")
    private LocalDateTime startTime;

    @NotNull(message = "End time wajib di isi!")
    private LocalDateTime endTime;

    private String reason;
}