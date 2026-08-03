package com.digitalhc.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToOne(mappedBy = "employee")
    private User user;

    private String email;
    private Long nik;
    private String namaLengkapEmployee;
    private Long nomerHpEmployee;
    private LocalDate tanggalLahirEmployee;
    private LocalDate tanggalBergabungEmployee;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private LocalDate createAt;
    private LocalDate updateAt;

    @OneToMany(mappedBy = "employee")
    private List<Leave> leaveList;

    @OneToOne(mappedBy = "employee")
    private LeaveBalance leaveBalancesList;

    @OneToMany(mappedBy = "employee")
    private List<Attendance> attendancesList;
}