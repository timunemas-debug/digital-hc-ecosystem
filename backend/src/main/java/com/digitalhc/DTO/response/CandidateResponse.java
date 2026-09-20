package com.digitalhc.DTO.response;

import com.digitalhc.model.Graduate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse {
    
    private String nama;
    private String email;
    private String nomerHp;
    private String pengalamanKerja;
    private String domisili;
    private Graduate pendidikanTerakhir;
}