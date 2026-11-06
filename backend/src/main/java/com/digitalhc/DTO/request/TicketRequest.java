package com.digitalhc.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequest {

    @NotBlank (message = "Subject wajib di isi!")
    private String subject;
    
    @NotBlank(message = "Wajib mengisi message!")
    private String message;

    @NotNull (message = "Department tujuan wajib di isi!")
    private Long departmentIdTujuan;
}