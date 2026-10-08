package com.digitalhc.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.UpdateUserRequest;
import com.digitalhc.DTO.request.UserRequest;
import com.digitalhc.DTO.response.UpdateUserResponse;
import com.digitalhc.DTO.response.UserResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.UpdateUserMapper;
import com.digitalhc.mapper.UserMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.User;
import com.digitalhc.model.UserStatus;
import com.digitalhc.repository.UserRepository;
import com.digitalhc.security.SecurityService;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UpdateUserMapper updateUserMapper;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    public UserService(UserRepository userRepository, UserMapper userMapper, UpdateUserMapper updateUserMapper, EmployeeService employeeService, PasswordEncoder passwordEncoder, SecurityService securityService){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.updateUserMapper = updateUserMapper;
        this.employeeService =employeeService;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
    }

    public UserResponse addUser(UserRequest request){
        
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId());

        if(userRepository.existsByEmployee(employee)){
            throw new BadRequestException("Employee sudah memiliki akun!");
        }

        User user = userMapper.toEntity(request);
        user.setEmail(employee.getEmail());
        user.setRole(request.getRole());
        user.setEmployee(employee);
        user.setStatus(UserStatus.AKTIF);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
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

        user.setRole(request.getRole());
        user.setStatus(request.getStatus());

        return updateUserMapper.toResponse(userRepository.save(user));
    }

    public void validationPasswordUser(String password){

        boolean firstCapital = Character.isUpperCase(password.charAt(0));
        boolean longCharacter = password.length() > 5;
        boolean hasSpecial = false;
        String specialCharacter = "!@#$%^&*()_+-=";

        if (password == null || password.isEmpty()) {
            throw new BadRequestException("Password tidak boleh kosong!");
        }
        
        for(Character c : password.toCharArray()){

            if (specialCharacter.indexOf(c) != -1) {
                hasSpecial = true;
            }
        }
        if (!firstCapital) {
            throw new BadRequestException("Wajib memiliki huruf kapital didepan!");
        }

        if (!longCharacter) {
            throw new BadRequestException("Jumlah character harus lebih dari 5!");
        }

        if (!hasSpecial) {
            throw new BadRequestException("Wajib menambah character unique dipassword!");
        }
    }

    public UserResponse updatePasswordUser(UserRequest request){

        Long userId = securityService.getCurrentUserId();


        validationPasswordUser(request.getPassword());

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFound("User tidak ditemukan!"));

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toResponse(userRepository.save(user));
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