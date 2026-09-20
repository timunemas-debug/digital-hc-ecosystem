package com.digitalhc.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.PermissionRequest;
import com.digitalhc.DTO.response.PermissionResponse;
import com.digitalhc.model.Permission;
import com.digitalhc.model.PermissionStatus;
import com.digitalhc.service.PermissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService){
        this.permissionService = permissionService;
    }

    @PostMapping("/add-permission")
    public PermissionResponse addPermission(@Valid @RequestBody PermissionRequest request){
        return permissionService.addPermission(request);
    }

    @PreAuthorize("hasRole('HC_MANAGER')")
    @PostMapping("/process-permission/{permissionId}/{status}")
    public void processPermission(@PathVariable Long permissionId, @PathVariable PermissionStatus status){
        permissionService.processPermission(permissionId, status);
    }

    @PreAuthorize("hasRole('HC_MANAGER')")
    @GetMapping("/get-all")
    public List<PermissionResponse> getAllPermission(){
        return permissionService.getAllPermission();
    }

    @PreAuthorize("hasRole('HC_MANAGER')")
    @GetMapping("/find-by-id/{permissionId}")
    public Permission findPermissionById(@PathVariable Long permissionId){
        return permissionService.findPermissionById(permissionId);
    }

    @PreAuthorize("hasRole('HC_MANAGER')")
    @GetMapping("/find-by-employeeid/{employeeId}")
    public List<PermissionResponse> findByEmployeeId(@PathVariable Long employeeId){
        return permissionService.findPermissionByEmployeeId(employeeId);
    }
}