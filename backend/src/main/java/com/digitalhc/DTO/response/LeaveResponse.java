package com.digitalhc.DTO.response;

import java.time.LocalDate;

import com.digitalhc.model.LeaveStatus;
import com.digitalhc.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponse {

    private LocalDate startDateLeave;
    private LocalDate endDateLeave;
    private String reasonLeave;
    private LeaveStatus status;
    private Role approvedBy;
}