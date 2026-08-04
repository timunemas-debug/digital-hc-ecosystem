package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.LeaveRequest;
import com.digitalhc.DTO.response.LeaveResponse;
import com.digitalhc.model.Leave;

@Component
public class LeaveMapper {
    
    public Leave toEntity(LeaveRequest request){

        Leave leave = new Leave();
        leave.setStartDateLeave(request.getStartDateLeave());
        leave.setEndDataLeave(request.getEndDateLeave());
        leave.setReasonLeave(request.getReasonLeave());

        return leave;
    }

    public LeaveResponse toResponse(Leave leave){
        return new LeaveResponse(leave.getStartDateLeave(),
                                 leave.getEndDataLeave(),
                                 leave.getReasonLeave(),
                                 leave.getStatus(),
                                 leave.getApprovedBy()
                                );
    }
}