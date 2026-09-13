package com.digitalhc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digitalhc.model.Employee;
import com.digitalhc.model.Leave;
import com.digitalhc.model.LeaveStatus;

import jakarta.persistence.LockModeType;

public interface LeaveRepository extends JpaRepository<Leave, Long>{
    
    long countByEmployeeAndStatus(Employee employee, LeaveStatus status);
    Long countByStartDateLeave(LocalDate date);

    List<Leave> findByEmployeeEmployeeId(Long employeeId);
    List<Leave> findByStatusLeave(LeaveStatus status);

    boolean existsByEmployeeEmployeeIdAndStatusAndStartDateLeaveLessThanEqualAndEndDateLeaveGreaterThanEqual(Long employeeId, LeaveStatus status, LocalDate startDateLeave, LocalDate endDateLeave);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Leave l WHERE l.leaveId = :leaveId")
    Optional<Leave> findByLeaveIdWithLock(@Param("leaveId") Long leaveId);
}