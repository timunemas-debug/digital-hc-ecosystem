package com.digitalhc.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectionRequest {
    
    @NotBlank(message = "Message wajib di isi!")
    private String message;
}