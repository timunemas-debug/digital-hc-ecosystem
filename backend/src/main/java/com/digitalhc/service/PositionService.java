package com.digitalhc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.PositionRequest;
import com.digitalhc.DTO.response.PositionResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.PositionMapper;
import com.digitalhc.model.Department;
import com.digitalhc.model.Position;
import com.digitalhc.repository.DepartmentRepository;
import com.digitalhc.repository.PositionRepository;

@Service
public class PositionService {
    
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final DepartmentRepository departmentRepository;

    public PositionService(PositionRepository positionRepository, PositionMapper positionMapper, DepartmentRepository departmentRepository){
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
        this.departmentRepository = departmentRepository;
    }

    public PositionResponse addPosition(PositionRequest request){

        Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFound("Department tidak ditemukan!"));

        Position position = positionMapper.toEntity(request);
        position.setDepartment(department);

        return positionMapper.toResponse(positionRepository.save(position));

    }

    public Position getPositionById(Long positionId){
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new ResourceNotFound("Position tidak ditemukan!"));
    }

    public PositionResponse getPositionResponse(Long positionId){

        Position position = getPositionById(positionId);

        return positionMapper.toResponse(position);
    }

    public List<PositionResponse> getAllPosition(){
        return positionRepository.findAll()
                .stream()
                .map(positionMapper::toResponse)
                .toList();
    }

    public PositionResponse updatePosition(Long positionId, PositionRequest request){

        Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFound("Department tidak ditemukan!"));

        Position position = getPositionById(positionId);
        position.setDepartment(department);
        position.setPositionName(request.getPositionName());
        position.setJobLevel(request.getJobLevel());

        return positionMapper.toResponse(positionRepository.save(position));
    }

    public void deletePosition(Long positionId){

        Position position = getPositionById(positionId);

        if (!position.getEmployees().isEmpty()) {
            throw new BadRequestException("Position masih digunakan employee!");
        }

        positionRepository.delete(position);
    }
}