package com.digitalhc.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.digitalhc.DTO.request.CandidateRequest;
import com.digitalhc.DTO.response.CandidateResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.CandidateMapper;
import com.digitalhc.model.Candidate;
import com.digitalhc.model.KotaCandidate;
import com.digitalhc.model.StatusCandidate;
import com.digitalhc.repository.CandidateRepository;
import com.digitalhc.security.SecurityService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

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

    @Transactional
    public List<CandidateResponse> importCandidate(MultipartFile file, KotaCandidate kota) throws IOException{
        List<Candidate> candidates = new ArrayList<>();

        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            
            Candidate candidate = new Candidate();
            candidate.setNama(record.getString("nama"));
            candidate.setEmail(record.getString("email"));
            candidate.setNomerHp(record.getString("nomerHp"));
            candidate.setPengalamanKerja(record.getString("pengalamanKerja"));
            candidate.setDomisili(record.getString("domisili"));

            String kotaString = record.getString("kota");
            KotaCandidate kotaCandidate = KotaCandidate.valueOf(kotaString);
            candidate.setKota(kotaCandidate);

            if (candidate.getKota() == kota) {
                candidate.setCreatedAt(LocalDateTime.now());
                candidate.setStatus(StatusCandidate.IMPORTED);
                candidates.add(candidate);
            }
        });
        List<Candidate> savedCandidate = candidateRepository.saveAll(candidates);

        return savedCandidate.stream()
                .map(candidateMapper::toMapResponse)
                .toList();
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

        if (status == null) {
            throw new BadRequestException("Status candidate null!");
        }

        if (candidate.getStatus() != StatusCandidate.IMPORTED && candidate.getStatus() != StatusCandidate.SCREENING) {
            throw new BadRequestException("Candidate sudah melewati prosess ini!");
        }

        candidate.setStatus(status);

        return candidateMapper.toMapResponse(candidateRepository.save(candidate));
    }
}