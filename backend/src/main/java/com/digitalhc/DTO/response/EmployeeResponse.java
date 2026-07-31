package com.digitalhc.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    
    private Long nik;
    private String namaLengkapEmployee;
    private Long nomerHpEmployee;
    private String tanggalLahirEmployee;
    private String tanggalBergabungEmployee;
    private String createAt;
    private String updateAt;
}