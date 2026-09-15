package com.digitalhc.DTO.response;

import java.time.LocalDateTime;

import com.digitalhc.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    
    private User recipient;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
}