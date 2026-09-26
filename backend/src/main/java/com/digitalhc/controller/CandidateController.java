package com.digitalhc.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalhc.DTO.request.CandidateRequest;
import com.digitalhc.DTO.response.CandidateResponse;
import com.digitalhc.model.KotaCandidate;
import com.digitalhc.model.StatusCandidate;
import com.digitalhc.service.CandidateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService){
        this.candidateService = candidateService;
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @PostMapping("/add-candidate")
    public CandidateResponse addCandidate(@Valid @RequestBody CandidateRequest request){
        return candidateService.addCandidate(request);
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @PostMapping("/add-with-file")
    public List<CandidateResponse> importCandidate(@RequestParam("file") MultipartFile file, @RequestParam("kota") KotaCandidate kota)throws IOException{
        return candidateService.importCandidate(file, kota);
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @GetMapping("/all-candidate")
    public List<CandidateResponse> getAll(){
        return candidateService.getAllCandidate();
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @DeleteMapping("/{candidateId}/delete")
    public void deleteCandidate(@PathVariable Long candidateId){
        candidateService.deleteCandidate(candidateId);
    }

    @PreAuthorize("hasRole('HC_OFFICER')")
    @PostMapping("/{candidateId}/update-candidate/{status}")
    public CandidateResponse updateCandidate(@PathVariable Long candidateId, @PathVariable StatusCandidate status){
        return candidateService.updateCandidateStatus(candidateId, status);
    }
}