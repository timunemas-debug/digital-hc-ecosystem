package com.digitalhc.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
    
    @NotNull(message = "NIK wajib di isi!")
    private Long nik;

    @NotBlank(message = "Nama Lengkap wajib di isi!")
    private String namaLengkapEmployee;

    @NotNull(message = "Nomer handphone wajib di isi!")
    private Long nomerHpEmployee;

    @NotBlank(message = "Tanggal lahir wajib di isi!")
    private String tanggalLahirEmployee;

    @NotBlank(message = "Tanggal bergabung wajib di isi!")
    private String tanggalBergabungEmployee;

    @NotBlank(message = "Wajib di isi!")
    private String createAt;
    
    @NotBlank(message = "Wajib di isi!")
    private String updateAt;
}
