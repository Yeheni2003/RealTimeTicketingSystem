package com.example.ticketingsystemui.model;

import com.example.ticketingsystemui.service.TicketService;

/**
 * The {@code TicketProducer}  class implements a producer that generates tickets
 * at a specified rate and adds them to the system. This class is used in a multi-threaded
 * environment where the producer thread continuously produces tickets until it is interrupted.
 *
 * <p>
 * The producer relies on {@link TicketService} to interact with the ticket storage
 * and updates the ticket pool with new tickets marked as "AVAILABLE".
 * </p>
 *
 * <p>
 * The {@code ticketReleaseRate} parameter determines how many tickets are produced
 * during each production cycle. The thread sleeps for a fixed interval (3 seconds)
 * between ticket production cycles to simulate real-time behavior.
 * </p>
 *
 * <p>
 * If the thread is interrupted, it will gracefully stop execution by checking the thread's
 * interruption status and exiting the loop.
 * </p>
 */
public class TicketProducer implements Runnable {

    /** The service used to produce and save tickets into the database. */
    private final TicketService ticketService;

    /** The rate at which tickets are produced during each cycle. */
    private final int ticketReleaseRate;

    /**
     * Constructs a {@code TicketProducer} instance with the given ticket service
     * and ticket production rate.
     *
     * @param ticketService      The {@link TicketService} used to handle ticket creation.
     * @param ticketReleaseRate  The number of tickets to produce per cycle.
     * @throws IllegalArgumentException if the provided {@code ticketReleaseRate} is zero or negative.
     */

    public TicketProducer(TicketService ticketService, int ticketReleaseRate) {
        this.ticketService = ticketService;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    /**
     * The main logic of the {@code TicketProducer}, which runs in a separate thread.
     * This method continuously produces tickets at the specified rate and adds them
     * to the system using {@link TicketService}.
     *
     * <p>
     * The thread will pause for 3 seconds between production cycles. If interrupted,
     * it will stop execution gracefully.
     * </p>
     */

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            /** Produce the specified number of tickets*/
            ticketService.produceTickets(ticketReleaseRate);
            System.out.println("Produced " + ticketReleaseRate + " tickets.");

            /**Pause for 3 seconds to simulate real time ticket generation.*/
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                /** Gracefully handle thread interruption and exit the loop.*/
                Thread.currentThread().interrupt();
            }
        }
    }
}