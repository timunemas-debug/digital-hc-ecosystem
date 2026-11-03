package com.digitalhc.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequest {
    
    @NotBlank(message = "Wajib mengisi message!")
    private String message;
}