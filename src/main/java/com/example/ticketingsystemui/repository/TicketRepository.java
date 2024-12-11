package com.example.ticketingsystemui.repository;

import com.example.ticketingsystemui.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {}
