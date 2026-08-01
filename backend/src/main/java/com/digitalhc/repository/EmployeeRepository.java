package com.digitalhc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Employee;
import com.digitalhc.model.EmployeeStatus;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    boolean existsByNamaLengkapEmployee(String name);

    Optional<Employee> findByNamaLengkapEmployee(String name);

    List<Employee> findByTanggalBergabungEmployee(LocalDate tanggal);
    List<Employee> findByTanggalBergabungEmployeeBetween(LocalDate start, LocalDate end);

    List<Employee> findByStatus(EmployeeStatus status);
}