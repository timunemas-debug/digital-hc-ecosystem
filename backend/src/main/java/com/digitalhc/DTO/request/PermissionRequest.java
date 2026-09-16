package com.digitalhc.DTO.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequest {
    
    @NotNull(message = "Start date wajib di isi!")
    private LocalDate startDatePermission;

    @NotNull(message = "End date wajib di isi!")
    private LocalDate endDatePermission;

    @NotBlank(message = "Reason wajib di isi!")
    private String reason;
}