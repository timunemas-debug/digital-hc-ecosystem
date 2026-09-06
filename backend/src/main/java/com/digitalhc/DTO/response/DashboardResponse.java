package com.digitalhc.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    
    private Long totalEmployee;
    private Long totalCheckIn;
    private Long totalCheckOut;
    private Long totalAttendance;
}