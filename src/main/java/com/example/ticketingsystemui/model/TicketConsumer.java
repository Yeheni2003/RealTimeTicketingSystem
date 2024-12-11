package com.example.ticketingsystemui.model;

import com.example.ticketingsystemui.service.TicketService;


/**
 * The {@code TicketConsumer} class represents a consumer that retrieves tickets
 * from the ticketing system at a specified retrieval rate.
 *
 * <p>This class implements the {@link Runnable} interface to allow it to run in a separate thread.
 */
public class TicketConsumer implements Runnable {
    private final TicketService ticketService;
    private final int customerRetrievalRate;

    /**
     * Constructs a {@code TicketConsumer} with a reference to the ticket service
     * and a retrieval rate.
     *
     * @param ticketService        the service for ticket operations
     * @param customerRetrievalRate the rate at which tickets are retrieved
     */
    public TicketConsumer(TicketService ticketService, int customerRetrievalRate) {
        this.ticketService = ticketService;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    /**
     * Runs the consumer thread to review tickets ata regular interval.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ticketService.retrieveTickets(customerRetrievalRate);
            System.out.println("Retrieved " + customerRetrievalRate + " tickets.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
