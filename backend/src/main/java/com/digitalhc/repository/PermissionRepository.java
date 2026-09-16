package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{
    
}