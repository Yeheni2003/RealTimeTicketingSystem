package com.example.ticketingsystemui.service;

import com.example.ticketingsystemui.model.Ticket;
import com.example.ticketingsystemui.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@code TicketService} class provides methods for producing, retrieving,
 * and counting tickets in the system.
 *
 * <p>It interacts with the {@link TicketRepository} for database operations.
 */
@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    /**
     * Constructs a {@code TicketService} with a reference to the ticket repository.
     *
     * @param ticketRepository the repository for ticket data
     */
    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    /**
     * Produces a specified number of tickets and marks them as "AVAILABLE".
     *
     * @param count the number of tickets to produce
     */
    public void produceTickets(int count) {
        for (int i = 0; i < count; i++) {
            ticketRepository.save(new Ticket("AVAILABLE"));
        }
    }


    /**
     * Retrieves a specified number of available tickets and marks them as "SOLD".
     *
     * @param count the number of tickets to retrieve
     * @return a list of tickets retrieved
     */
    public List<Ticket> retrieveTickets(int count) {
        List<Ticket> tickets = ticketRepository.findAll()
                .stream()
                .filter(ticket -> "AVAILABLE".equals(ticket.getStatus()))
                .limit(count)
                .toList();

        tickets.forEach(ticket -> ticket.setStatus("SOLD"));
        ticketRepository.saveAll(tickets);
        return tickets;
    }

    /**
     * Counts the number of available tickets in the system.
     *
     * @return the number of available tickets
     */
    public long countAvailableTickets() {
        return ticketRepository.findAll()
                .stream()
                .filter(ticket -> "AVAILABLE".equals(ticket.getStatus()))
                .count();
    }
}
