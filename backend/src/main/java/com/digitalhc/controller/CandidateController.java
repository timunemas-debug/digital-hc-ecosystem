package com.digitalhc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalhc.service.CandidateService;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService){
        this.candidateService = candidateService;
    }
}