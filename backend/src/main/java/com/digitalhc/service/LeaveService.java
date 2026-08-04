package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.repository.LeaveRepository;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository){
        this.leaveRepository = leaveRepository;
    }
}