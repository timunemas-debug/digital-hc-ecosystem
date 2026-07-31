package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    
}
