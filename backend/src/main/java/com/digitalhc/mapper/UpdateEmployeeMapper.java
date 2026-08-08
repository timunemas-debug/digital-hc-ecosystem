package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.response.UpdateEmployeeResponse;
import com.digitalhc.model.Employee;

@Component
public class UpdateEmployeeMapper {
    
    public UpdateEmployeeResponse mapToResponse(Employee employee){

        UpdateEmployeeResponse response = new UpdateEmployeeResponse();
        response.setEmail(employee.getEmail());
        response.setNik(employee.getNik());
        response.setNamaLengkapEmployee(employee.getNamaLengkapEmployee());
        response.setNomerHpEmployee(employee.getNomerHpEmployee());
        response.setTanggalLahirEmployee(employee.getTanggalLahirEmployee());

        return response;
    }
}