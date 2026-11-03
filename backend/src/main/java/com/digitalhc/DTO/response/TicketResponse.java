package com.digitalhc.DTO.response;

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
    private String message;
    private StatusTicket statusTicket;
    private Long createdBy;
}
