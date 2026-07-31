package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.EmployeeRequest;
import com.digitalhc.DTO.response.EmployeeResponse;
import com.digitalhc.mapper.EmployeeMapper;
import com.digitalhc.model.Employee;
import com.digitalhc.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper){
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponse addEmployee(EmployeeRequest request){
        employeeRepository.existsByNamaLengkapEmployee(request.getNamaLengkapEmployee());

        Employee employee = employeeMapper.toEntity(request);

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }
}