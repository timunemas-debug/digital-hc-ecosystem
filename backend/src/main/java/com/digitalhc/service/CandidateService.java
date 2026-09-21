package com.digitalhc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.CandidateRequest;
import com.digitalhc.DTO.response.CandidateResponse;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.CandidateMapper;
import com.digitalhc.model.Candidate;
import com.digitalhc.model.StatusCandidate;
import com.digitalhc.repository.CandidateRepository;
import com.digitalhc.security.SecurityService;

import jakarta.transaction.Transactional;

@Service
public class CandidateService {
    
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final SecurityService securityService;

    public CandidateService(CandidateRepository candidateRepository, CandidateMapper candidateMapper, SecurityService securityService){
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
        this.securityService = securityService;
    }

    @Transactional
    public CandidateResponse addCandidate(CandidateRequest request){

        Candidate candidate = candidateMapper.toEntity(request);
        candidate.setCreatedAt(LocalDateTime.now());
        candidate.setStatus(StatusCandidate.IMPORTED);

        return candidateMapper.toMapResponse(candidateRepository.save(candidate));
    }

    public List<CandidateResponse> getAllCandidate(){
        return candidateRepository.findAll()
                .stream()
                .map(candidateMapper::toMapResponse)
                .toList();
    }

    public void deleteCandidate(Long candidateId){
        candidateRepository.deleteById(candidateId);
    }

    @Transactional
    public CandidateResponse updateCandidateStatus(Long candidateId, StatusCandidate status){

        Candidate candidate = candidateRepository.findById(candidateId)
            .orElseThrow(() -> new ResourceNotFound("Candidate dengan id tersebut tidak ditemukan!"));
    }

}