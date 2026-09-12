package com.digitalhc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.LeaveRequest;
import com.digitalhc.DTO.response.LeaveResponse;
import com.digitalhc.service.LeaveService;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService){
        this.leaveService = leaveService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/add-leave")
    public LeaveResponse addLeave(LeaveRequest request){
        return leaveService.addLeave(request);
    }
}