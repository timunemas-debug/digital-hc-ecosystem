package com.digitalhc.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.InterviewRequest;
import com.digitalhc.DTO.response.InterviewResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.InterviewMapper;
import com.digitalhc.model.Candidate;
import com.digitalhc.model.Interview;
import com.digitalhc.model.InterviewStatus;
import com.digitalhc.repository.CandidateRepository;
import com.digitalhc.repository.InterviewRepository;

@Service
public class InterviewService {
    
    private final InterviewMapper interviewMapper;
    private final InterviewRepository interviewRepository;
    private final CandidateRepository candidateRepository;

    public InterviewService(InterviewMapper interviewMapper, InterviewRepository interviewRepository, CandidateRepository candidateRepository){
        this.interviewMapper = interviewMapper;
        this.interviewRepository = interviewRepository;
        this.candidateRepository = candidateRepository;
    }

    public InterviewResponse scheduleInterview(Long candidateId, InterviewRequest request){

        Candidate candidate = candidateRepository.findById(candidateId)
            .orElseThrow(() -> new ResourceNotFound("Candidate tidak ditemukan!"));

        Interview interview = interviewMapper.toEntity(request);
        
        if (interview.getJadwalInterview().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Jadwal interview tidak valid!");
        }

        interview.setCandidate(candidate);
        interview.setStatus(InterviewStatus.SCHEDULED);
        interview.setCreatedAt(LocalDateTime.now());

        return interviewMapper.toMapResponse(interviewRepository.save(interview));
    }
}