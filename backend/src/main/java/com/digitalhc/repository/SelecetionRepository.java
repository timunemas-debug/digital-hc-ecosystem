package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Selection;

public interface SelecetionRepository extends JpaRepository<Selection, Long> {
    
}