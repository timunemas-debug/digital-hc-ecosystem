package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.response.UpdateUserResponse;
import com.digitalhc.model.User;

@Component
public class UpdateUserMapper {
    
    public UpdateUserResponse toResponse(User user){

        UpdateUserResponse response = new UpdateUserResponse();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());

        return response;
    }
}