package com.digitalhc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.UpdateUserRequest;
import com.digitalhc.DTO.request.UserRequest;
import com.digitalhc.DTO.response.UpdateUserResponse;
import com.digitalhc.DTO.response.UserResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.UpdateUserMapper;
import com.digitalhc.mapper.UserMapper;
import com.digitalhc.model.User;
import com.digitalhc.model.UserStatus;
import com.digitalhc.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UpdateUserMapper updateUserMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, UpdateUserMapper updateUserMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.updateUserMapper = updateUserMapper;
    }

    public UserResponse addUser(UserRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email sudah terdaftar");
        }

        User user = userMapper.toEntity(request);
        user.setStatus(UserStatus.AKTIF);

        return userMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUser(){
        
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public User getUserById(Long userId){

        return userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFound("User tidak ditemukan!"));
    }

    public UserResponse getUserResponseById(Long userId){

        User user = getUserById(userId);

        return userMapper.toResponse(user);
    }

    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request){

        User user = getUserById(userId);

        if(!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email sudah terdaftar");
        }

        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());

        return updateUserMapper.toResponse(userRepository.save(user));
    }

    public UserResponse nonAktifUser(Long userId){

        User user = getUserById(userId);

        user.setStatus(UserStatus.NONAKTIF);

        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse lockedUser(Long userId){

        User user = getUserById(userId);

        user.setStatus(UserStatus.LOCKED);

        return userMapper.toResponse(userRepository.save(user));
    }
}