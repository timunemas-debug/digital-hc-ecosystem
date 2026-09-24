package com.digitalhc.DTO.request;

import com.digitalhc.model.Graduate;
import com.digitalhc.model.KotaCandidate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRequest {
    
    @NotBlank(message = "Nama tidak boleh kosong!")
    private String nama;

    @NotBlank(message = "Email tidak boleh kosong!")
    @Email
    private String email;

    @NotBlank(message = "Nomer hp tidak boleh kosong!")
    private String nomerHp;

    @NotBlank(message = "Pengalaman kerja tidak boleh kosong!")
    private String pengalamanKerja;

    @NotBlank(message = "Domisili tidak boleh kosong!")
    private String domisili;

    @NotNull(message = "Kota tidak boleh kosong!")
    private KotaCandidate kota;

    @NotNull(message = "Pendidikan terakhir wajib di isi!")
    private Graduate pendidikanTerakhir;
}