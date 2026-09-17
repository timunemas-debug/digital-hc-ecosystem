package com.digitalhc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digitalhc.model.LeaveBalance;

import jakarta.persistence.LockModeType;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long>{
    
    Optional<LeaveBalance> findByEmployeeEmployeeId(Long employeeId);

    boolean existsByEmployeeEmployeeId(Long employeeId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT lb FROM LeaveBalance WHERE lb.employeeId = :employeeId")
    Optional<LeaveBalance> findByEmployeeEmployeeIdWithLock(@Param("employeeId") Long employeeId);
}