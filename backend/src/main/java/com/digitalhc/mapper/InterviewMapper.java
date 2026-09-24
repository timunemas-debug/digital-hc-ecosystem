package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.InterviewRequest;
import com.digitalhc.DTO.response.InterviewResponse;
import com.digitalhc.model.Interview;

@Component
public class InterviewMapper {
    
    public Interview toEntity(InterviewRequest request){
        Interview interview = new Interview();
        interview.setJadwalInterview(request.getJadwalInterview());
        interview.setStatus(request.getStatus());

        return interview;
    }

    public InterviewResponse toMapResponse(Interview interview){
        return new InterviewResponse(interview.getJadwalInterview(),
                                     interview.getStatus(),
                                     interview.getCreatedAt());
    }
}