package com.digitalhc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.PositionRequest;
import com.digitalhc.DTO.response.PositionResponse;
import com.digitalhc.service.PositionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/position")
public class PositionController {
    
    private final PositionService positionService;

    public PositionController(PositionService positionService){
        this.positionService = positionService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public PositionResponse addPosition(@Valid @RequestBody PositionRequest request){
        return positionService.addPosition(request);
    }
}