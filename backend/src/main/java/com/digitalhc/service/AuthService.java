package com.digitalhc.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.LoginRequest;
import com.digitalhc.DTO.request.RegisterRequest;
import com.digitalhc.DTO.response.LoginResponse;
import com.digitalhc.DTO.response.RegisterResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.mapper.UserMapper;
import com.digitalhc.model.User;
import com.digitalhc.repository.UserRepository;
import com.digitalhc.security.CustomUserDetails;
import com.digitalhc.security.JwtService;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, UserMapper userMapper, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(RegisterRequest request){

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BadRequestException("Email sudah digunakan!");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = jwtService.generateToken(userDetails);
        
        return new LoginResponse(jwt);
    }
}