package com.digitalhc.DTO.response;

import com.digitalhc.model.DepartementName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    
    private DepartementName departementName;
    private String deskription;
}