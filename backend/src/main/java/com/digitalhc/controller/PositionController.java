package com.digitalhc.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{positionId}")
    public PositionResponse getPositionById(@PathVariable Long positionId){
        return positionService.getPositionResponse(positionId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<PositionResponse> getALlPosition(){
        return positionService.getAllPosition();
    }

    @PatchMapping("/{positionId}")
    public PositionResponse updatePosition(@PathVariable Long positionId, @Valid @RequestBody PositionRequest request){
        return positionService.updatePosition(positionId, request);
    }

    @DeleteMapping("/{positionId}")
    public void deletePosition(@PathVariable Long positionId){
        positionService.deletePosition(positionId);
    }
}