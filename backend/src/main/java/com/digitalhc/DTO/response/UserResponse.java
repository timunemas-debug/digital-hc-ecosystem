package com.digitalhc.DTO.response;


import com.digitalhc.model.EmployeeStatus;
import com.digitalhc.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String email;
    private String password;
    private Role role;
    private EmployeeStatus status;
}