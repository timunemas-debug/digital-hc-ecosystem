package com.digitalhc.DTO.response;

import java.time.LocalDate;

import com.digitalhc.model.Employee;
import com.digitalhc.model.LeaveStatus;

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
    private Employee approvedBy;
}