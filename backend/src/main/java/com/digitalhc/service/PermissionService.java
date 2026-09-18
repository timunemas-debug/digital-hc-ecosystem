package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.PermissionRequest;
import com.digitalhc.DTO.response.PermissionResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.PermissionMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.model.Permission;
import com.digitalhc.model.PermissionStatus;
import com.digitalhc.repository.EmployeeRepository;
import com.digitalhc.repository.PermissionRepository;
import com.digitalhc.security.SecurityService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService {
    
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;
    private final SecurityService securityService;
    private final EmployeeRepository employeeRepository;

    public PermissionService(PermissionMapper permissionMapper, PermissionRepository permissionRepository, SecurityService securityService, EmployeeRepository employeeRepository){
        this.permissionMapper = permissionMapper;
        this.permissionRepository = permissionRepository;
        this.securityService = securityService;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public PermissionResponse addPermission(PermissionRequest request){
        
        Long employeeId = securityService.getCurrentUserId();

        Employee employee = employeeRepository.findByEmployeeId(employeeId)
            .orElseThrow(() -> new ResourceNotFound("Employee tidak ditemukan!"));

        if (request.getStartDatePermission().isAfter(request.getEndDatePermission())) {
            throw new BadRequestException("Tanggal permission tidak valid!");
        }

        Permission permission = permissionMapper.toEntity(request);
        permission.setEmployee(employee);
        permission.setStatus(PermissionStatus.SUBMITTED);

        return permissionMapper.toMapResponse(permissionRepository.save(permission));
    }
}