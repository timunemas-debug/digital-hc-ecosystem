package com.digitalhc.DTO.request;

import com.digitalhc.model.Department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequest {
    
    @NotBlank(message = "Wajib mengisi message!")
    private String message;

    @NotNull (message = "Wajib menambahkan departement tujuan!")
    private Department department;
}