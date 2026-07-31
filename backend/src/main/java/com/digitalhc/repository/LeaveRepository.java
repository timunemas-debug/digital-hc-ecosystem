package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Long>{
    
}