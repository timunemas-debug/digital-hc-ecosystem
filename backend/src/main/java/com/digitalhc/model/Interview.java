package com.digitalhc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Interview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewId;

    private LocalDateTime jadwalInterview;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;
    
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "candidateId")
    private Candidate candidate;

    public Interview(){
    }
}