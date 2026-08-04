package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.DepartmentRequest;
import com.digitalhc.DTO.response.DepartmentResponse;
import com.digitalhc.model.Department;

@Component
public class DepartmentMapper {
    
    public Department toEntity(DepartmentRequest request){

        Department department = new Department();
        department.setDepartmentName(request.getDepartementName());
        department.setDescription(request.getDescription());
        
        return department;
    }

    public DepartmentResponse toResponse(Department department){
        return new DepartmentResponse(department.getDepartmentName(),
                                      department.getDescription());
    }
}