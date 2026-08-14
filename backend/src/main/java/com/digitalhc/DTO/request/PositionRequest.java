package com.digitalhc.DTO.request;

import com.digitalhc.model.JobLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionRequest {

    @NotBlank(message = "Position wajib di isi!")
    private String positionName;

    @NotNull(message = "Job level wajib di isi!")
    private JobLevel jobLevel;

    @NotNull(message = "Deparment wajib di isi!")
    private Long departmentId;
}