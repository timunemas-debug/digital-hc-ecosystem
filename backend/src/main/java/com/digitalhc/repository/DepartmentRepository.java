package com.digitalhc.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Department;
import com.digitalhc.model.DepartmentName;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    
    Optional<Department> findByDepartmentName(DepartmentName departmentName);

    boolean existsByDepartmentName(DepartmentName departmentName);
}