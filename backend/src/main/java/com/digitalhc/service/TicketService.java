package com.digitalhc.service;

import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.TicketRequest;
import com.digitalhc.DTO.response.TicketResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.TicketMapper;
import com.digitalhc.model.StatusTicket;
import com.digitalhc.model.Ticket;
import com.digitalhc.model.User;
import com.digitalhc.repository.TicketRepository;
import com.digitalhc.repository.UserRepository;
import com.digitalhc.security.SecurityService;

@Service
public class TicketService {

    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public TicketService(TicketMapper ticketMapper, TicketRepository ticketRepository, SecurityService securityService, UserRepository userRepository){
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    public TicketResponse createTicket(TicketRequest request){

        Long currentUser = securityService.getCurrentUserId();

        User user = userRepository.findById(currentUser)
            .orElseThrow(() -> new ResourceNotFound("User tidak ditemukan!"));
        
        Ticket ticket = ticketMapper.toEntity(request);
        if (ticketRepository.existsById(ticket.getId())) {
            throw new BadRequestException("Ticket sudah dibuat!");
        }
        ticket.setCreatedBy(user.getUserId());
        ticket.setStatusTicket(StatusTicket.SUBMITTED);

        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketMapper.mapToResponse(savedTicket);
    }
}