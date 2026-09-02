package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.OvertimeRequest;
import com.digitalhc.DTO.response.OvertimeResponse;
import com.digitalhc.model.Overtime;

@Component
public class OvertimeMapper {

    public Overtime toEntity(OvertimeRequest request){

        Overtime overtime = new Overtime();
        overtime.setDate(request.getDate());
        overtime.setStartTime(request.getStartTime());
        overtime.setEndTime(request.getEndTime());
        overtime.setReason(request.getReason());

        return overtime;
    }

    public OvertimeResponse toMapResponse(Overtime overtime){

        return new OvertimeResponse(overtime.getDate(),
                                    overtime.getStartTime(),
                                    overtime.getEndTime(),
                                    overtime.getReason());
    }
}