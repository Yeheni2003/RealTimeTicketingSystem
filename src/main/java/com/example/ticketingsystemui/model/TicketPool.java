package com.example.ticketingsystemui.model;

import java.util.LinkedList;

/**
 * The {@code TicketPool} class manages a pool of tickets using a synchronized
 * queue-like structure. It supports adding and retrieving tickets while ensuring
 * thread safety and proper synchronization.
 *
 * <p>This class uses {@link LinkedList} to hold tickets and allows producers
 * to add tickets and consumers to retrieve tickets with synchronized methods.
 */
public class TicketPool {
    private final LinkedList<Integer> tickets = new LinkedList<>();
    private final int maxTicketCapacity;
    private int availableTickets = 0;

    /**
     * Constructs a {@code TicketPool} with a specified maximum ticket capacity.
     *
     * @param maxTicketCapacity the maximum number of tickets the pool can hold
     */
    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Adds tickets to the pool in a thread-safe manner.
     *
     * <p>If the pool is full, the method waits until space becomes available.
     *
     * @param ticketCount the number of tickets to add
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public synchronized void addTicket(int ticketCount) throws InterruptedException {
        while (tickets.size() + ticketCount > maxTicketCapacity) {
            System.out.println("Pool is full. Waiting for tickets to be retrieved...");
            wait();
        }
        for (int i = 0; i < ticketCount; i++) {
            tickets.add(tickets.size() + 1);
        }
        availableTickets += ticketCount;
        System.out.println("Tickets Added: " + ticketCount + " | Available Tickets: " + getAvailableTickets());
        notifyAll();
    }

    /**
     * Retrieves tickets from the pool in a thread-safe manner.
     *
     * <p>If the pool does not have enough tickets, the method waits until tickets are added.
     *
     * @param ticketCount the number of tickets to retrieve
     * @return the number of tickets successfully retrieved
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public synchronized int retrieveTickets(int ticketCount) throws InterruptedException {
        while (tickets.size() < ticketCount) {
            System.out.println("Pool is empty. Waiting for tickets to be added...");
            wait();
        }

        for (int i = 0; i < ticketCount; i++) {
            tickets.removeFirst();
        }
        availableTickets -= ticketCount;
        System.out.println(ticketCount + " Tickets Sold | Available Tickets: " + getAvailableTickets());

        notifyAll();
        return ticketCount;
    }

    /**
     * Returns the number of tickets currently available in the pool.
     *
     * @return the number of available tickets
     */
    public synchronized int getAvailableTickets() {
        return availableTickets;
    }
}
