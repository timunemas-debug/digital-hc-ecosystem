package com.digitalhc.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "overtime",
    uniqueConstraints = @UniqueConstraint(name = "uq_employee_date", columnNames = {"employee_id", "overtime_date"})
)
@Getter
@Setter
@AllArgsConstructor
public class Overtime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long overtimeId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "overtime_date", nullable = false)
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    
    @Enumerated(EnumType.STRING)
    private OverTimeStatus status;

    @Enumerated(EnumType.STRING)
    private Role approvedBy;

    private LocalDateTime approvedAt;

    public Overtime(){
    }
}