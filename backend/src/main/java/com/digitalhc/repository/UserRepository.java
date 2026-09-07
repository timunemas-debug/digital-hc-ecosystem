package com.digitalhc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Employee;
import com.digitalhc.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    boolean existsByEmployee(Employee employee);

    Optional<User> findByEmailIgnoreCase(String email);
}