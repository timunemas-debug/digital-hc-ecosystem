package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.EmployeeRequest;
import com.digitalhc.DTO.response.EmployeeResponse;
import com.digitalhc.model.Employee;

@Component
public class EmployeeMapper {
    
    public Employee toEntity(EmployeeRequest request){

        Employee employee = new Employee();

        employee.setEmail(request.getEmail());
        employee.setNik(request.getNik());
        employee.setNamaLengkapEmployee(request.getNamaLengkapEmployee());
        employee.setNomerHpEmployee(request.getNomerHpEmployee());
        employee.setTanggalLahirEmployee(request.getTanggalLahirEmployee());
        employee.setTanggalBergabungEmployee(request.getTanggalBergabungEmployee());

        return employee;
    }

    public EmployeeResponse toResponse(Employee employee){
        return new EmployeeResponse(employee.getEmail(),
                                    employee.getNik(),
                                    employee.getNamaLengkapEmployee(),
                                    employee.getNomerHpEmployee(),
                                    employee.getTanggalLahirEmployee(),
                                    employee.getTanggalBergabungEmployee(),
                                    employee.getPosition().getPositionName()
                                    );
    }
}