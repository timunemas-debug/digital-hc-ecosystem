package com.digitalhc.DTO.request;

import com.digitalhc.model.Role;
import com.digitalhc.model.UserStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    
    @NotBlank(message = "Email wajib di isi!")
    private String email;

    @NotNull(message = "Role wajib di isi!")
    private Role role;

    private UserStatus status;
}