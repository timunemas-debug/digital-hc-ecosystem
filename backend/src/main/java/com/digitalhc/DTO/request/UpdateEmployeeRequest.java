package com.digitalhc.DTO.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeRequest {
    
    @NotBlank(message = "Nama tidak boleh kosong!")
    private String namaLengkapEmployee;

    @NotNull(message = "Nik tidak boleh kosong!")
    private Long nik;

    @NotNull(message = "Nomer hp tidak boleh kosong!")
    private Long nomerHpEmployee;

    @NotNull(message = "Tanggal lahir tidak boleh kosong!")
    private LocalDate tanggalLahirEmployee;
}