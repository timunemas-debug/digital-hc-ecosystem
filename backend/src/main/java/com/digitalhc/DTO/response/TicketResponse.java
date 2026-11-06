package com.digitalhc.DTO.response;

import java.time.LocalDateTime;

import com.digitalhc.model.Department;
import com.digitalhc.model.StatusTicket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private String subject;
    private String message;
    private StatusTicket statusTicket;
    private Department departmentPengirim;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
