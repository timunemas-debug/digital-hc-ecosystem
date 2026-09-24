package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    
}