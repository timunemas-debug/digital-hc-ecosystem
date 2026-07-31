package com.digitalhc.DTO.request;

import com.digitalhc.model.DepartementName;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequest {
    
    @NotNull(message = "Department wajib di isi!")
    private DepartementName departementName;

    private String deskription;
}