package com.digitalhc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.EmployeeRequest;
import com.digitalhc.DTO.response.EmployeeResponse;
import com.digitalhc.service.EmployeeService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/add-employee")
    public EmployeeResponse addEmployee(@Valid @RequestBody EmployeeRequest request){
        return employeeService.addEmployee(request);
    }
}
