package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.CandidateRequest;
import com.digitalhc.DTO.response.CandidateResponse;
import com.digitalhc.model.Candidate;

@Component
public class CandidateMapper {
    
    public Candidate toEntity(CandidateRequest request){

        Candidate candidate = new Candidate();
        candidate.setNama(request.getNama());
        candidate.setEmail(request.getEmail());
        candidate.setNomerHp(request.getNomerHp());
        candidate.setPengalamanKerja(request.getPengalamanKerja());
        candidate.setDomisili(request.getDomisili());
        candidate.setPendidikanTerakhir(request.getPendidikanTerakhir());

        return candidate;
    }

    public CandidateResponse toMapResponse(Candidate candidate){
        return new CandidateResponse(candidate.getNama(),
                                     candidate.getEmail(),
                                     candidate.getNomerHp(),
                                     candidate.getPengalamanKerja(),
                                     candidate.getDomisili(),
                                     candidate.getKota(),
                                     candidate.getPendidikanTerakhir());
    }
}