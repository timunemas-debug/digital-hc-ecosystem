package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
}