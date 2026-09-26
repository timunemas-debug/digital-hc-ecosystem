package com.digitalhc.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.InterviewRequest;
import com.digitalhc.DTO.response.InterviewResponse;
import com.digitalhc.model.InterviewStatus;
import com.digitalhc.service.InterviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/interview")
public class InterviewController {
    
    private final InterviewService interviewService;
    
    public InterviewController(InterviewService interviewService){
        this.interviewService = interviewService;
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @PostMapping("/{candidateId}/schedule-interview")
    public InterviewResponse scheduleInterview(@PathVariable Long candidateId, @Valid @RequestBody InterviewRequest request){
        return interviewService.scheduleInterview(candidateId, request);
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @GetMapping("/get-all-interview")
    public List<InterviewResponse> getAll(){
        return interviewService.getAllInterview();
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @GetMapping("/{interviewId}/get-interview")
    public InterviewResponse getInterviewByInterviewId(@PathVariable Long interviewId){
        return interviewService.getInterviewByInterviewId(interviewId);
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @PatchMapping("/{interviewId}/update-status/{status}")
    public InterviewResponse updateStatus(@PathVariable Long interviewId, @PathVariable InterviewStatus status){
        return interviewService.updateStatusInterview(interviewId, status);
    }
}