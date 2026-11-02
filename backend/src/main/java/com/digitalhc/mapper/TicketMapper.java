package com.digitalhc.mapper;

import org.springframework.stereotype.Component;

import com.digitalhc.DTO.request.TicketRequest;
import com.digitalhc.DTO.response.TicketResponse;
import com.digitalhc.model.Ticket;

@Component
public class TicketMapper {
    
    public Ticket toEntity(TicketRequest request){

        Ticket ticket = new Ticket();
        ticket.setMessage(request.getMessage());
        ticket.setDepartment(request.getDepartment());

        return ticket;
    }

    public TicketResponse mapToResponse(Ticket ticket){
        return new TicketResponse(ticket.getMessage(),
                                  ticket.getStatusTicket());
    }
}