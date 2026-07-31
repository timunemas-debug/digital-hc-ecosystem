package com.digitalhc.DTO.request;

import com.digitalhc.model.EmployeeStatus;
import com.digitalhc.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Email wajib di isi!")
    @Email
    private String email;

    @NotBlank(message = "Password wajib di isi!")
    private String password;

    @NotNull(message = "Role wajib di isi!")
    private Role role;

    private EmployeeStatus status;
}
