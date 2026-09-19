package com.digitalhc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digitalhc.model.Permission;

import jakarta.persistence.LockModeType;

public interface PermissionRepository extends JpaRepository<Permission, Long>{
    
    List<Permission> findByEmployeeEmployeeId(Long employeeId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Permission p WHERE p.permissionId = :permissionId")
    Optional<Permission> findByPermissionIdWithLock(@Param("permissionId") Long permissionId);
}