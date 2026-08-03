package com.digitalhc.DTO.request;

import com.digitalhc.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotNull(message = "Employee wajib di pilih!")
    private Long employeeId;

    @NotBlank(message = "Password wajib di isi!")
    private String password;

    @NotNull(message = "Role wajib di isi!")
    private Role role;
}