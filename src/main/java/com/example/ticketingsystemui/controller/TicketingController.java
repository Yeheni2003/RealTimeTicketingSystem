package com.example.ticketingsystemui.controller;

import com.example.ticketingsystemui.service.TicketService;
import com.example.ticketingsystemui.model.TicketProducer;
import com.example.ticketingsystemui.model.TicketConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code TicketingController} class provides REST API endpoints to manage
 * a ticketing system. It allows starting, stopping, and retrieving the current
 * state of the system, including available tickets.
 *
 * <p>The controller manages ticket production and consumption by creating
 * producer and consumer threads. It interacts with the {@link TicketService}
 * to handle ticket operations.
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketingController {

    private final TicketService ticketService;
    private Thread producerThread, consumerThread;


    /**
     * Constructs a {@code TicketingController} and injects the required {@link TicketService}.
     *
     * @param ticketService the service responsible for managing ticket operations
     */
    @Autowired
    public TicketingController(TicketService ticketService) {
        this.ticketService = ticketService;

    }

    /**
     * Starts the ticket system by initiating producer and consumer threads.
     *
     * <p>The producer generates tickets at the specified {@code releaseRate},
     * and the consumer retrieves tickets at the specified {@code retrievalRate}.</p>
     *
     * @param releaseRate   the rate at which tickets are released (produced)
     * @param retrievalRate the rate at which tickets are retrieved (consumed)
     * @return a message indicating that the ticket system has started
     */
    @PostMapping("/start")
    public String startTicketSystem(@RequestParam int releaseRate, @RequestParam int retrievalRate) {
        producerThread = new Thread(new TicketProducer(ticketService, releaseRate));
        consumerThread = new Thread(new TicketConsumer(ticketService, retrievalRate));
        producerThread.start();
        consumerThread.start();
        return "Ticket system started!";
    }

    /**
     * Stops the ticket system by interrupting the producer and consumer threads.
     *
     * @return a message indicating that the ticket system has been stopped
     */
    @PostMapping("/stop")
    public String stopTicketSystem() {
        if (producerThread != null) producerThread.interrupt();
        if (consumerThread != null) consumerThread.interrupt();
        return "Ticket system stopped!";
    }

    /**
     * Retrieves the count of available tickets in the system.
     *
     * <p>This method delegates the task to {@link TicketService#countAvailableTickets()}
     * to obtain the current number of tickets.
     *
     * @return the number of available tickets
     */
    @GetMapping("/available")
    public long getAvailableTickets() {
        return ticketService.countAvailableTickets();
    }
}
