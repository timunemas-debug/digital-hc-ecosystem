package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.mapper.NotificationMapper;
import com.digitalhc.repository.NotificationRepository;

@Service
public class NotificationService {
    
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationMapper notificationMapper, NotificationRepository notificationRepository){
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
    }
}