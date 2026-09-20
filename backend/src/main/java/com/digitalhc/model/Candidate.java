package com.digitalhc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Candidate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    private String nama;
    private String email;
    private String nomerHp;
    private String pengalamanKerja;
    private String domisili;

    @Enumerated(EnumType.STRING)
    private Graduate pendidikanTerakhir;

    @Enumerated(EnumType.STRING)
    private StatusCandidate status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Candidate(){
    }
}
