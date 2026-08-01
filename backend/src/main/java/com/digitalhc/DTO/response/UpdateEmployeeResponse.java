package com.digitalhc.DTO.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeResponse {
    
    private Long nik;
    private String namaLengkapEmployee;
    private Long nomerHpEmployee;
    private LocalDate tanggalLahirEmployee;
}