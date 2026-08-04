package com.digitalhc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.DepartmentRequest;
import com.digitalhc.DTO.response.DepartmentResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.DepartmentMapper;
import com.digitalhc.model.Department;
import com.digitalhc.repository.DepartmentRepository;

@Service
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper){
        this.departmentRepository = departmentRepository;
        this.departmentMapper =departmentMapper;
    }

    public DepartmentResponse addDepartment(DepartmentRequest request){

        if(departmentRepository.existsByDepartmentName(request.getDepartementName())){
            throw new BadRequestException("Department sudah dibuat!");
        }

        Department department = departmentMapper.toEntity(request);

        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    public List<DepartmentResponse> getAllDepartment(){
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    public DepartmentResponse getDepartmentById(Long departmentId){

        Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new ResourceNotFound("Department tidak ditemukan!"));

        return departmentMapper.toResponse(department);
    }

    public DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request){

        Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new ResourceNotFound("Department tidak ditemukan!"));

        if(!department.getDepartmentName().equals(request.getDepartementName()) &&
            departmentRepository.existsByDepartmentName(request.getDepartementName())){
                throw new BadRequestException("Department sudah dibuat!");
            }

        department.setDepartmentName(request.getDepartementName());
        department.setDescription(request.getDescription());

        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    public void deleteDepartment(Long departmentId){

        if(!departmentRepository.existsById(departmentId)){
            throw new ResourceNotFound("Department tidak ditemukan!");
        }
        departmentRepository.deleteById(departmentId);
    }
}