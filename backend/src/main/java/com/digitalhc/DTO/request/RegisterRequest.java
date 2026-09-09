package com.digitalhc.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Email wajib di isi!")
    @Email
    private String email;

    @NotBlank(message = "Password wajib di isi!")
    private String password;
}