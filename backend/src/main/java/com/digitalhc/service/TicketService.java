package com.digitalhc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.digitalhc.DTO.request.TicketRequest;
import com.digitalhc.DTO.response.TicketResponse;
import com.digitalhc.exception.BadRequestException;
import com.digitalhc.exception.ResourceNotFound;
import com.digitalhc.mapper.TicketMapper;
import com.digitalhc.model.Department;
import com.digitalhc.model.StatusTicket;
import com.digitalhc.model.Ticket;
import com.digitalhc.model.User;
import com.digitalhc.repository.DepartmentRepository;
import com.digitalhc.repository.TicketRepository;
import com.digitalhc.repository.UserRepository;
import com.digitalhc.security.SecurityService;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public TicketService(TicketMapper ticketMapper,
                         TicketRepository ticketRepository,
                         SecurityService securityService,
                         UserRepository userRepository,
                         DepartmentRepository departmentRepository){

        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public TicketResponse createTicket(TicketRequest request){

        Long currentUser = securityService.getCurrentUserId();

        User user = userRepository.findById(currentUser)
            .orElseThrow(() -> new ResourceNotFound("User tidak ditemukan!"));

        Department department = departmentRepository.findById(request.getDepartmentIdTujuan())
            .orElseThrow(() -> new ResourceNotFound("Department tidak ditemukan!"));
        
        Ticket ticket = ticketMapper.toEntity(request);

        ticket.setCreatedBy(user.getUserId());
        ticket.setDepartmentTujuan(department);
        ticket.setDepartmentPengirim(user.getEmployee().getPosition().getDepartment());
        ticket.setStatusTicket(StatusTicket.SUBMITTED);
        ticket.setCreatedAt(LocalDateTime.now());
        
        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketMapper.mapToResponse(savedTicket);
    }

    public Ticket findById(Long ticketId){
        return ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ResourceNotFound("Ticket tidak ditemukan!"));
    }

    public List<TicketResponse> findAll(){
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::mapToResponse)
                .toList();
    }

    @Transactional
    public TicketResponse acceptTickets(Long ticketId){
        
        Long currentId = securityService.getCurrentUserId();
    
        User user = userRepository.findById(currentId)
            .orElseThrow(() -> new ResourceNotFound("User tidak ditemukan!"));

        Ticket ticket = findById(ticketId);

        if (ticket.getDepartmentTujuan().getDepartmentId() != user.getEmployee().getPosition().getDepartment().getDepartmentId()) {
            throw new BadRequestException("Ticket bukan milik user tersebut");
        }

        if (ticket.getStatusTicket() != StatusTicket.SUBMITTED) {
            throw new BadRequestException("Ticket sudah tidak berlaku!");
        }

        ticket.setStatusTicket(StatusTicket.APPROVED);
        ticket.setUpdateAt(LocalDateTime.now());
        
        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketMapper.mapToResponse(savedTicket);
    }

    @Transactional
    public void rejectTickets(Long ticketId){
        Long currentId = securityService.getCurrentUserId();

        User user = userRepository.findById(currentId)
            .orElseThrow(() -> new ResourceNotFound("User tidak ditemukan!"));

        Ticket ticket = findById(ticketId);

        if (ticket.getDepartmentTujuan().getDepartmentId() != user.getEmployee().getPosition().getDepartment().getDepartmentId()) {
            throw new BadRequestException("Ticket bukan milik user tersebut");
        }

        if (ticket.getStatusTicket() != StatusTicket.SUBMITTED) {
            throw new BadRequestException("Ticket sudah tidak bisa dilakukan reject!");
        }

        ticket.setStatusTicket(StatusTicket.REJECTED);
        ticket.setUpdateAt(LocalDateTime.now());

        ticketRepository.save(ticket);
    }
}