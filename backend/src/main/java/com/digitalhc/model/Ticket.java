package com.digitalhc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
@AllArgsConstructor
@Entity
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private String message;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department departmentPengirim;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department departmentTujuan;
    
    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    private Long createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    public Ticket(){
    }
}