package com.digitalhc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
    List<Notification> findByRecipientUserId(Long userId);
    List<Notification> findByRecipientUserIdAndIsRead(Long userId, boolean isRead);
}