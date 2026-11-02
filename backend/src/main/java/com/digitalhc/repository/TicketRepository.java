package com.digitalhc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalhc.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
    
}