package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.UserRequest;
import com.digitalhc.DTO.response.UserResponse;
import com.digitalhc.model.User;

@Component
public class UserMapper {
    
    public User toEntity(UserRequest request){

        User user = new User();
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        return user;
    }

    public UserResponse toResponse(User user){

        return new UserResponse(user.getUserId(),
                                user.getEmployee().getEmployeeId(),
                                user.getEmployee().getEmail(),
                                user.getRole(),
                                user.getStatus());
    }
}