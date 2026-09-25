package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.SelectionRequest;
import com.digitalhc.DTO.response.SelectionResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.SelectionMapper;
import com.digitalhc.model.Candidate;
import com.digitalhc.model.Selection;
import com.digitalhc.model.StatusCandidate;
import com.digitalhc.repository.CandidateRepository;
import com.digitalhc.repository.SelectionRepository;

@Service
public class SelectionService {
    
    private final SelectionMapper selectionMapper;
    private final SelectionRepository selecetionRepository;
    private final CandidateRepository candidateRepository;

    public SelectionService(SelectionMapper selectionMapper, SelectionRepository selecetionRepository, CandidateRepository candidateRepository){
        this.selectionMapper = selectionMapper;
        this.selecetionRepository = selecetionRepository;
        this.candidateRepository = candidateRepository;
    }

    public SelectionResponse recordResult(Long candidateId, SelectionRequest request){
        
        Candidate candidate = candidateRepository.findById(candidateId)
            .orElseThrow(() -> new ResourceNotFound("Candidate dengan id tersebut tidak ditemukan!"));

        if (candidate.getStatus() != StatusCandidate.PASSED && candidate.getStatus() != StatusCandidate.INTERVIEW) {
            throw new BadRequestException("Status candidate tidak sesuai!");
        }

        Selection selection = selectionMapper.toEntity(request);
        selection.setCandidate(candidate);

        candidate.setStatus(request.getStatus());

        Selection savedSelection = selecetionRepository.save(selection);
        candidateRepository.save(candidate);

        return selectionMapper.toMapResponse(selecetionRepository.save(savedSelection));
    }

    public SelectionResponse getSelectionById(Long selectionId){

        Selection selection = selecetionRepository.findById(selectionId)
            .orElseThrow(() -> new ResourceNotFound("Selection dengan id tersebut tidak ditemukan!"));

        return selectionMapper.toMapResponse(selection);
    }
}