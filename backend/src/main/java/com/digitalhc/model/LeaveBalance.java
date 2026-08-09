package com.digitalhc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leavesBalanceId;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private int totalLeaves = 12;
    private int usedLeaves;
}
