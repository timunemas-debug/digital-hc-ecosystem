package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.mapper.OvertimeMapper;
import com.digitalhc.repository.OvertimeRepository;

@Service
public class OvertimeService {
    
    private final OvertimeRepository overtimeRepository;
    private final OvertimeMapper overtimeMapper;

    public OvertimeService(OvertimeRepository overtimeRepository, OvertimeMapper overtimeMapper){
        this.overtimeRepository = overtimeRepository;
        this.overtimeMapper = overtimeMapper;
    }
}