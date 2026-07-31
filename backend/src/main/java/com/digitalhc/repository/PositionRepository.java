package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
    
}