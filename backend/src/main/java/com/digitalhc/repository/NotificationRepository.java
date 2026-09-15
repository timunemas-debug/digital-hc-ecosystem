package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
}