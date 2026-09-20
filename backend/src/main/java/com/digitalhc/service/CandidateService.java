package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.CandidateRequest;
import com.digitalhc.DTO.response.CandidateResponse;
import com.digitalhc.mapper.CandidateMapper;
import com.digitalhc.model.Candidate;
import com.digitalhc.repository.CandidateRepository;

@Service
public class CandidateService {
    
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    public CandidateService(CandidateRepository candidateRepository, CandidateMapper candidateMapper){
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    public CandidateResponse addCandidate(CandidateRequest request){

        Candidate candidate = candidateMapper.toEntity(request);

        return candidateMapper.toMapResponse(candidateRepository.save(candidate));
    }

}