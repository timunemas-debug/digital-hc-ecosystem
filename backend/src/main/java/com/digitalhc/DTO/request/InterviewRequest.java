package com.digitalhc.DTO.request;

import java.time.LocalDateTime;

import com.digitalhc.model.InterviewStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewRequest {
    
    @NotNull(message = "Wajib masukan jadwal interview")
    private LocalDateTime jadwalInterview;
}