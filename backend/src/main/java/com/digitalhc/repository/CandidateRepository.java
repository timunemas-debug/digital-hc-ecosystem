package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long>{
    
}