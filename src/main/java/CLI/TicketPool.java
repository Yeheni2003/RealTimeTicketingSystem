package CLI;

import java.util.LinkedList;

/**
 * The {@code TicketPool} class is a thread-safe implementation of a pool for managing tickets.
 * It supports producers adding tickets and consumers retrieving tickets concurrently while
 * ensuring synchronization and maintaining a maximum capacity.
 */
public class TicketPool {

    /** A linked list to store the tickets in the pool. */
    private final LinkedList<Integer> tickets = new LinkedList<>();

    /** The maximum number of tickets that the pool can hold. */
    private final int maxTicketCapacity;

    /** The current number of available tickets in the pool. */
    private int availableTickets =0;

    /**
     * Constructs a {@code TicketPool} with a specified maximum ticket capacity.
     *
     * @param maxTicketCapacity the maximum number of tickets the pool can hold.
     */
    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Adds tickets to the pool up to the specified count.
     * <p>
     * If adding tickets exceeds the pool's maximum capacity, the method blocks
     * until enough space becomes available.
     * </p>
     *
     * @param ticketCount the number of tickets to add.
     * @throws InterruptedException if the thread is interrupted while waiting.
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
     * Retrieves a specified number of tickets from the pool.
     * <p>
     * If there are not enough tickets available in the pool, the method blocks
     * until tickets are added by the producer.
     * </p>
     *
     * @param ticketCount the number of tickets to retrieve.
     * @return the number of tickets successfully retrieved.
     * @throws InterruptedException if the thread is interrupted while waiting.
     */
    public synchronized int retrieveTickets(int ticketCount) throws InterruptedException {
        while (tickets.size() < ticketCount) {
            System.out.println("Pool is empty. Waiting for tickets to be added...");
            wait(); // Release the lock and wait until notified
        }

        for (int i = 0; i < ticketCount; i++) {
            tickets.removeFirst();
        }
        availableTickets -= ticketCount; // Update the count of available tickets
        System.out.println(ticketCount + " Tickets Sold | Available Tickets: " + getAvailableTickets());

        notifyAll(); // Notify producers that space is available
        return ticketCount;
    }

    /**
     * Returns the current number of available tickets in the pool.
     *
     * @return the number of available tickets.
     */
    public synchronized int getAvailableTickets() {
        return availableTickets;
    }


}