package com.digitalhc.DTO.request;

import com.digitalhc.model.StatusCandidate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectionRequest {
    
    @NotNull(message = "Status wajib di isi!")
    private StatusCandidate status;

    @NotBlank(message = "Message wajib di isi!")
    private String message;
}