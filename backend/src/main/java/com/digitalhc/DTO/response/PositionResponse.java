package com.digitalhc.DTO.response;

import com.digitalhc.model.JobLevel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionResponse {
    
    private String positionName;
    private JobLevel jobLevel;
}