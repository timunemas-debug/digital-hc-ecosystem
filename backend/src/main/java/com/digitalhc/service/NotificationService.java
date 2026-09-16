package com.digitalhc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.response.NotificationResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.NotificationMapper;
import com.digitalhc.model.Notification;
import com.digitalhc.model.User;
import com.digitalhc.repository.NotificationRepository;
import com.digitalhc.security.SecurityService;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {
    
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final SecurityService securityService;

    public NotificationService(NotificationMapper notificationMapper, NotificationRepository notificationRepository, UserService userService, SecurityService securityService){
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.securityService = securityService;
    }

    public void createNotification(Long userId, String message){

        User user = userService.getUserById(userId);

        Notification notification = new Notification();
        notification.setRecipient(user);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    @Transactional
    public NotificationResponse markAsRead(Long notificationId){

        Long userId = securityService.getCurrentUserId();

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFound("Notification tidak ditemukan!"));

        if (!notification.getRecipient().getUserId().equals(userId)) {
            throw new BadRequestException("Notification bukan milik user!");
        }

        notification.setRead(true);

        return notificationMapper.mapToResponse(notificationRepository.save(notification));
    }

    @Transactional
    public void markAllRead(){
        
        Long userId = securityService.getCurrentUserId();

        List<Notification> notifications = notificationRepository.findByRecipientUserIdAndIsRead(userId, false);

        notifications.forEach(notification -> notification.setRead(true));

        notificationRepository.saveAll(notifications);
    }
}