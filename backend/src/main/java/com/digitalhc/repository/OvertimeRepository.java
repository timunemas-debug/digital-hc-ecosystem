package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Overtime;

public interface OvertimeRepository extends JpaRepository <Overtime, Long>{
    
}