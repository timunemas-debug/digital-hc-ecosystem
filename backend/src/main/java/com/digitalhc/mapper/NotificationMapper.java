package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.response.NotificationResponse;
import com.digitalhc.model.Notification;

@Component
public class NotificationMapper {
    
    public NotificationResponse mapToResponse(Notification notification){
        return new NotificationResponse(notification.getRecipient(),
                                        notification.getMessage(),
                                        notification.isRead(),
                                        notification.getCreatedAt());
    }
}