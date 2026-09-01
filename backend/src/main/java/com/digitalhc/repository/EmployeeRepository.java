package com.digitalhc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digitalhc.model.Employee;
import com.digitalhc.model.EmployeeStatus;

import jakarta.persistence.LockModeType;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    boolean existsByNamaLengkapEmployee(String name);

    Optional<Employee> findByNamaLengkapEmployee(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Employee e WHERE e.employeeId = :employeeId")
    Optional<Employee> findByEmployeeIdWithLock(@Param("employeeId") Long employeeId);

    List<Employee> findByTanggalBergabungEmployee(LocalDate tanggal);
    List<Employee> findByTanggalBergabungEmployeeBetween(LocalDate start, LocalDate end);
    List<Employee> findByEmployeeId(Long employeeId);
    List<Employee> findByStatus(EmployeeStatus status);
}