package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Selection;

public interface SelectionRepository extends JpaRepository<Selection, Long> {
    
}