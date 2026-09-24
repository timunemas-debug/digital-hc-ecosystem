package com.digitalhc.service;

import java.time.LocalDateTime;
import java.util.List;

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

import jakarta.transaction.Transactional;

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

    @Transactional
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

    public List<InterviewResponse> getAllInterview(){
        return interviewRepository.findAll()
                .stream()
                .map(interviewMapper::toMapResponse)
                .toList();
    }

    @Transactional
    public InterviewResponse getInterviewByInterviewId(Long interviewId){
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ResourceNotFound("Interview dengan id tesebut tidak ditemukan!"));

        return interviewMapper.toMapResponse(interview);
    }

    public InterviewResponse updateStatusInterview(Long interviewId, InterviewStatus status){

        Interview interview = interviewRepository.findById(interviewId)
            .orElseThrow(() -> new ResourceNotFound("Interview dengan id tersebut tidak ditemukan!"));

        if (status == null) {
            throw new BadRequestException("Status tidak boleh kosong!");
        }

        interview.setStatus(status);

        return interviewMapper.toMapResponse(interviewRepository.save(interview));
    }
}