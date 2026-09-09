package com.digitalhc.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.DepartmentRequest;
import com.digitalhc.DTO.response.DepartmentResponse;
import com.digitalhc.service.DepartmentService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public DepartmentResponse addDepartment(@Valid @RequestBody DepartmentRequest request){
        return departmentService.addDepartment(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public List<DepartmentResponse> getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{departmentId}")
    public DepartmentResponse getDepartmentById(@PathVariable Long departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{departmentId}/update-department")
    public DepartmentResponse updateDepartment(@PathVariable Long departmentId, @Valid @RequestBody DepartmentRequest request){
        return departmentService.updateDepartment(departmentId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{departmentId}/delete")
    public void deleteDepartment(@PathVariable Long departmentId){
        departmentService.deleteDepartment(departmentId);
    }
}