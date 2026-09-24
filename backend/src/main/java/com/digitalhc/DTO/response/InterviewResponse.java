package com.digitalhc.DTO.response;

import java.time.LocalDateTime;

import com.digitalhc.model.InterviewStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponse {
    
    private LocalDateTime jadwalInterview;
    private InterviewStatus status;
    private LocalDateTime createdAt;
}