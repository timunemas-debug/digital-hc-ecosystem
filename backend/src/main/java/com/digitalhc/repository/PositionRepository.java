package com.digitalhc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
    
    Optional<Position> findByEmployeeEmployeeId(Long employeeId);
}