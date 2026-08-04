package com.digitalhc.DTO.response;

import com.digitalhc.model.DepartmentName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    
    private DepartmentName departementName;
    private String description;
}