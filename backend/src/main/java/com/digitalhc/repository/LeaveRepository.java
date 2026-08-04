package com.digitalhc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Employee;
import com.digitalhc.model.Leave;
import com.digitalhc.model.LeaveStatus;

public interface LeaveRepository extends JpaRepository<Leave, Long>{
    
    long countByEmployeeAndStatus(Employee employee, LeaveStatus status);

    List<Leave> findByEmployeeEmployeeId(Long employeeId);
}