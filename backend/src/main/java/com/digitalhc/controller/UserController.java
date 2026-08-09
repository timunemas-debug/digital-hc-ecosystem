package com.digitalhc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.UpdateUserRequest;
import com.digitalhc.DTO.request.UserRequest;
import com.digitalhc.DTO.response.UpdateUserResponse;
import com.digitalhc.DTO.response.UserResponse;
import com.digitalhc.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/add-user")
    public UserResponse addUser(@Valid @RequestBody UserRequest request){
        return userService.addUser(request);
    }

    @GetMapping
    public List<UserResponse> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId){
        return userService.getUserResponseById(userId);
    }

    @PutMapping("/{userId}/update-user")
    public UpdateUserResponse updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequest request){
        return userService.updateUser(userId, request);
    }

    @PutMapping("/{userId}/non-aktif-user")
    public UserResponse nonAktifUser(@PathVariable Long userId){
        return userService.nonAktifUser(userId);
    }

    @PutMapping("/{userId}/locked-user")
    public UserResponse lockedUser(@PathVariable Long userId){
        return userService.lockedUser(userId);
    }
}