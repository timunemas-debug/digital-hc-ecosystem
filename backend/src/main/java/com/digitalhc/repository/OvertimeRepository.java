package com.digitalhc.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digitalhc.model.Overtime;

import jakarta.persistence.LockModeType;

public interface OvertimeRepository extends JpaRepository <Overtime, Long>{
    
    boolean existsByEmployeeEmployeeIdAndDate(Long employeeId, LocalDate date);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Overtime o WHERE o.overtimeId = :overtimeId")
    Optional<Overtime> findByOvertimeIdWithLock(@Param("overtimeId") Long overtimeId);
}