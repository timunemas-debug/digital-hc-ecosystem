package com.digitalhc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.PermissionRequest;
import com.digitalhc.DTO.response.PermissionResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.PermissionMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.EmployeeStatus;
import com.digitalhc.model.Permission;
import com.digitalhc.model.PermissionStatus;
import com.digitalhc.model.User;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.repository.PermissionRepository;
import com.digitalhc.repository.UserRepository;
import com.digitalhc.security.SecurityService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService {
    
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;
    private final SecurityService securityService;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public PermissionService(PermissionMapper permissionMapper, PermissionRepository permissionRepository, SecurityService securityService, EmployeeRepository employeeRepository, UserRepository userRepository, NotificationService notificationService){
        this.permissionMapper = permissionMapper;
        this.permissionRepository = permissionRepository;
        this.securityService = securityService;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public PermissionResponse addPermission(PermissionRequest request){
        
        Long employeeId = securityService.getCurrentUserId();

        Employee employee = employeeRepository.findByEmployeeId(employeeId)
            .orElseThrow(() -> new ResourceNotFound("Employee tidak ditemukan!"));

        if (employee.getStatus() != EmployeeStatus.AKTIF) {
            throw new BadRequestException("Employee sudah tidak aktif!");
        }

        if (request.getStartDatePermission().isAfter(request.getEndDatePermission())) {
            throw new BadRequestException("Tanggal permission tidak valid!");
        }

        Permission permission = permissionMapper.toEntity(request);
        permission.setEmployee(employee);
        permission.setStatus(PermissionStatus.SUBMITTED);

        return permissionMapper.toMapResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAllPermission(){
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toMapResponse)
                .toList();
    }

    public Permission findPermissionById(Long permissionId){
        
        return permissionRepository.findById(permissionId)
            .orElseThrow(() -> new ResourceNotFound("Permission tidak ditemukan!"));
    }

    public List<PermissionResponse> findPermissionByEmployeeId(Long employeeId){
        
        return permissionRepository.findByEmployeeEmployeeId(employeeId)
                .stream()
                .map(permissionMapper::toMapResponse)
                .toList();
    }

    @Transactional
    public void processPermission(Long permissionId, PermissionStatus status){

        Permission permission = permissionRepository.findByPermissionIdWithLock(permissionId)
            .orElseThrow(() -> new ResourceNotFound("Permission tidak ditemukan!"));

        if (permission.getEmployee() == null) {
            throw new ResourceNotFound("Employee pemilik permission tidak ditemukan!");
        }

        Employee employee = permission.getEmployee();

        if (permission.getStatus() != PermissionStatus.SUBMITTED) {
            throw new BadRequestException("Status permission sudah diproses dan tidak dapat diubah!");
        }

        if (status != PermissionStatus.APPROVED && status != PermissionStatus.REJECTED) {
            throw new BadRequestException("Status hanya dapat menjadi approved atau rejected!");
        }

        if (employee.getStatus() != EmployeeStatus.AKTIF) {
            throw new BadRequestException("Employee sudah tidak aktif!");
        }

        if (permission.getAprovedBy() != null) {
            throw new BadRequestException("Permission sudah di proses!");
        }


        Long userId = securityService.getCurrentUserId();

        User currentUser = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFound("User tidak ditemukan!"));

        permission.setAprovedBy(currentUser);
        permission.setStatus(status);

        permissionRepository.save(permission);
        notificationService.createNotification(employee.getUser().getUserId(), "Permission anda " + status);
    }
}