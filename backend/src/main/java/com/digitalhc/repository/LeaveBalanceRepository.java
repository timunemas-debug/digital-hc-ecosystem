package com.digitalhc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long>{
    
    Optional<LeaveBalance> findByEmployeeEmployeeId(Long employeeId);

    boolean existsByEmployeeEmployeeId(Long employeeId);
}