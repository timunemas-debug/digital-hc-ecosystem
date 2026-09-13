package com.digitalhc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long>{
    
    List<LeaveBalance> findByEmployeeEmployeeId(Long employeeId);

    boolean existsByEmployeeEmployeeId(Long employeeId);
}