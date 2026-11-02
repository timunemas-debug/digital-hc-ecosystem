package com.digitalhc.service;

import com.digitalhc.mapper.TicketMapper;
import com.digitalhc.repository.TicketRepository;
import com.digitalhc.security.SecurityService;

public class TicketService {

    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final SecurityService securityService;

    public TicketService(TicketMapper ticketMapper, TicketRepository ticketRepository, SecurityService securityService){
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.securityService = securityService;
    }
}