package com.digitalhc.DTO.response;

import com.digitalhc.model.Role;
import com.digitalhc.model.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {
    
    private String email;
    private Role role;
    private UserStatus status;
}