package com.digitalhc.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitalhc.DTO.request.UpdateUserRequest;
import com.digitalhc.DTO.request.UserRequest;
import com.digitalhc.DTO.response.UpdateUserResponse;
import com.digitalhc.DTO.response.UserResponse;
import com.digitalhc.mapper.UpdateUserMapper;
import com.digitalhc.mapper.UserMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.Role;
import com.digitalhc.model.User;
import com.digitalhc.model.UserStatus;
import com.digitalhc.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    UpdateUserMapper updateUserMapper;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    UserService userService;

    @Test
    public void shouldAddUser(){

        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setEmail("example@gmail.com");
        
        User user = new User();
        user.setUserId(2L);
        user.setPassword("12345");
        user.setRole(Role.ROLE_EMPLOYEE);
        user.setStatus(UserStatus.AKTIF);

        UserResponse response = new UserResponse();
        response.setEmployeeId(1L);
        response.setEmail("example@gmail.com");
        response.setRole(Role.ROLE_EMPLOYEE);
        response.setStatus(UserStatus.AKTIF);
        response.setUserId(2L);

        UserRequest request = new UserRequest();
        request.setEmployeeId(employee.getEmployeeId());
        request.setPassword("12345");
        request.setRole(Role.ROLE_EMPLOYEE);

        when(employeeService.getEmployeeById(1L))
                .thenReturn(employee);

        when(userRepository.existsByEmployee(employee))
                .thenReturn(false);

        when(userRepository.save(user))
                .thenReturn(user);

        when(userMapper.toEntity(request))
                .thenReturn(user);

        when(userMapper.toResponse(user))
                .thenReturn(response);

        UserResponse result = userService.addUser(request);

        assertEquals(1L, result.getEmployeeId());
        assertEquals(2L, result.getUserId());
        assertEquals("example@gmail.com", result.getEmail());
        assertEquals(Role.ROLE_EMPLOYEE, result.getRole());
        assertEquals(UserStatus.AKTIF, result.getStatus());

        verify(employeeService).getEmployeeById(1L);
        verify(userRepository).existsByEmployee(employee);
        verify(userRepository).save(user);
        verify(userMapper).toEntity(request);
        verify(userMapper).toResponse(user);
    }

    @Test
    public void shouldGetAllUser(){

        User user = new User();
        user.setUserId(1L);
        user.setPassword("12345");

        UserResponse response = new UserResponse();
        response.setUserId(1L);
        response.setEmail("example@gmail.com");

        when(userRepository.findAll())
                .thenReturn(List.of(user));

        when(userMapper.toResponse(user))
                .thenReturn(response);

        List<UserResponse> result = userService.getAllUser();

        assertEquals(1L, result.get(0).getUserId());
        assertEquals("example@gmail.com", result.get(0).getEmail());

        verify(userRepository).findAll();
        verify(userMapper).toResponse(user);
    }

    @Test
    public void shouldGetUserById(){

        User user = new User();
        user.setUserId(1L);
        user.setStatus(UserStatus.AKTIF);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getUserId());
        assertEquals(UserStatus.AKTIF, result.getStatus());

        verify(userRepository).findById(1L);
    }

    @Test
    public void shouldGetUserResponseById(){

        User user = new User();
        user.setUserId(1L);
        user.setStatus(UserStatus.AKTIF);

        UserResponse response = new UserResponse();
        response.setUserId(1L);
        response.setEmail("example@gmail.com");
        response.setStatus(UserStatus.AKTIF);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userMapper.toResponse(user))
                .thenReturn(response);

        UserResponse result = userService.getUserResponseById(1L);

        assertEquals(1L, result.getUserId());
        assertEquals(UserStatus.AKTIF, result.getStatus());
        assertEquals("example@gmail.com", result.getEmail());

        verify(userRepository).findById(1L);
        verify(userMapper).toResponse(user);
    }

    @Test
    public void shouldUpdateUser(){

        User user = new User();
        user.setUserId(1L);
        user.setStatus(UserStatus.AKTIF);
        user.setRole(Role.ROLE_EMPLOYEE);

        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("example@gmail.com");
        request.setRole(Role.ROLE_ADMIN);

        UpdateUserResponse response = new UpdateUserResponse();
        response.setEmail("example@gmail.com");
        response.setRole(Role.ROLE_ADMIN);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user))
                .thenReturn(user);

        when(updateUserMapper.toResponse(user))
                .thenReturn(response);

        UpdateUserResponse result = userService.updateUser(1L, request);

        assertEquals("example@gmail.com", result.getEmail());
        assertEquals(Role.ROLE_ADMIN, result.getRole());

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        verify(updateUserMapper).toResponse(user);
    }

    @Test
    public void shouldNonAktifUser(){

        User user = new User();
        user.setUserId(1L);
        user.setRole(Role.ROLE_EMPLOYEE);
        user.setStatus(UserStatus.NONAKTIF);

        UserResponse response = new UserResponse();
        response.setUserId(1L);
        response.setRole(Role.ROLE_EMPLOYEE);
        response.setStatus(UserStatus.NONAKTIF);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user))
                .thenReturn(user);

        when(userMapper.toResponse(user))
                .thenReturn(response);

        UserResponse result = userService.nonAktifUser(1L);

        assertEquals(1L, result.getUserId());
        assertEquals(Role.ROLE_EMPLOYEE, result.getRole());
        assertEquals(UserStatus.NONAKTIF, result.getStatus());

        verify(userRepository).findById(1L);
        verify(userMapper).toResponse(user);
        verify(userRepository).save(user);
    }

        @Test
    public void shouldLockedUser(){

        User user = new User();
        user.setUserId(1L);
        user.setRole(Role.ROLE_EMPLOYEE);
        user.setStatus(UserStatus.LOCKED);

        UserResponse response = new UserResponse();
        response.setUserId(1L);
        response.setRole(Role.ROLE_EMPLOYEE);
        response.setStatus(UserStatus.LOCKED);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user))
                .thenReturn(user);

        when(userMapper.toResponse(user))
                .thenReturn(response);

        UserResponse result = userService.nonAktifUser(1L);

        assertEquals(1L, result.getUserId());
        assertEquals(Role.ROLE_EMPLOYEE, result.getRole());
        assertEquals(UserStatus.LOCKED, result.getStatus());

        verify(userRepository).findById(1L);
        verify(userMapper).toResponse(user);
        verify(userRepository).save(user);
    }
}