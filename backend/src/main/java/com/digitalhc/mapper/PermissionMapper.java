package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.PermissionRequest;
import com.digitalhc.DTO.response.PermissionResponse;
import com.digitalhc.model.Permission;

@Component
public class PermissionMapper {
    
    public Permission toEntity(PermissionRequest request){
        Permission permission = new Permission();
        permission.setStartDatePermission(request.getStartDatePermission());
        permission.setEndDatePermission(request.getEndDatePermission());
        permission.setReason(request.getReason());

        return permission;
    }

    public PermissionResponse toMapResponse(Permission permission){
        return new PermissionResponse(permission.getStartDatePermission(),
                                      permission.getEndDatePermission(),
                                      permission.getReason(),
                                      permission.getStatus(),
                                      permission.getAprovedBy());
    }
}