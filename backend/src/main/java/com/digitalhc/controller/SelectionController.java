package com.digitalhc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.DTO.request.SelectionRequest;
import com.digitalhc.DTO.response.SelectionResponse;
import com.digitalhc.service.SelectionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/selection")
public class SelectionController {
    
    private final SelectionService selectionService;

    public SelectionController(SelectionService selectionService){
        this.selectionService = selectionService;
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @PostMapping("/{candidateId}/result")
    public SelectionResponse recordResult(@PathVariable Long candidateId, @Valid @RequestBody SelectionRequest request){
        return selectionService.recordResult(candidateId, request);
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @GetMapping("/{selectionId}/get-selection")
    public SelectionResponse getSelectionById(@PathVariable Long selectionId){
        return selectionService.getSelectionById(selectionId);
    }
}