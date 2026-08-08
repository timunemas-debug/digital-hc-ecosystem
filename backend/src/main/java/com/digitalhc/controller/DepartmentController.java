package com.digitalhc.controller;

import java.util.List;

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

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentResponse addDepartment(@Valid @RequestBody DepartmentRequest request){
        return departmentService.addDepartment(request);
    }

    @GetMapping
    public List<DepartmentResponse> getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    @GetMapping("/{departmentId}")
    public DepartmentResponse getDepartmentById(@PathVariable Long departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @PutMapping("/{departmentId}/update-department")
    public DepartmentResponse updateDepartment(@PathVariable Long departmentId, @Valid @RequestBody DepartmentRequest request){
        return departmentService.updateDepartment(departmentId, request);
    }

    @DeleteMapping("{departmentId")
    public void deleteDepartment(@PathVariable Long departmentId){
        departmentService.deleteDepartment(departmentId);
    }
}