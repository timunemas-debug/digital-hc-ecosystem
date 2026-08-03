package com.digitalhc.DTO.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
    
    @NotBlank(message = "Email wajib di isi!")
    @Email
    private String email;

    @NotNull(message = "NIK wajib di isi!")
    private Long nik;

    @NotBlank(message = "Nama Lengkap wajib di isi!")
    private String namaLengkapEmployee;

    @NotNull(message = "Nomer handphone wajib di isi!")
    private Long nomerHpEmployee;

    @NotBlank(message = "Tanggal lahir wajib di isi!")
    private LocalDate tanggalLahirEmployee;

    @NotBlank(message = "Tanggal bergabung wajib di isi!")
    private LocalDate tanggalBergabungEmployee;

    @NotBlank(message = "Wajib di isi!")
    private LocalDate createAt;
    
    @NotBlank(message = "Wajib di isi!")
    private LocalDate updateAt;
}