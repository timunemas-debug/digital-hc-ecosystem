package com.digitalhc.DTO.response;

import java.time.LocalDate;

import com.digitalhc.model.PermissionStatus;
import com.digitalhc.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {
    
    private LocalDate startDatePermission;
    private LocalDate endDatePermission;
    private String reason;
    private PermissionStatus status;
    private User approvedBy;
}