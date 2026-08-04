package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.PositionRequest;
import com.digitalhc.DTO.response.PositionResponse;
import com.digitalhc.model.Position;

@Component
public class PositionMapper {
    
    public Position toEntity(PositionRequest request){

        Position position = new Position();
        position.setPositionName(request.getPositionName());
        position.setJobLevel(request.getJobLevel());

        return position;
    }

    public PositionResponse toResponse(Position position){
        return new PositionResponse(position.getPositionName(),
                                    position.getJobLevel(),
                                    position.getDepartment().getDepartmentName()
                                    );
    }
}