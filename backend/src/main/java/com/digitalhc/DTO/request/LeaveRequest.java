package com.digitalhc.DTO.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequest {

    @NotNull(message = "Wajib di isi!")
    private LocalDate startDateLeave;

    @NotNull(message = "Wajin di isi!")
    private LocalDate endDateLeave;

    @NotBlank(message = "Reason wajin di isi!")
    private String reasonLeave;
}