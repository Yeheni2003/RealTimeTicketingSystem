package CLI;

/**
 * The {@code TicketProducer} class represents a producer in the producer-consumer pattern
 * that generates tickets at a specified release rate and adds them to the {@link TicketPool}.
 * This class implements the {@link Runnable} interface to allow concurrent execution.
 */
class TicketProducer implements Runnable {

    /** The shared {@link TicketPool} where tickets are added. */
    private final TicketPool ticketPool;

    /** The rate at which tickets are released (tickets per second). */
    private final int ticketReleaseRate;

    /**
     * Constructs a {@code TicketProducer} with the specified {@code TicketPool} and release rate.
     *
     * @param ticketPool        The shared ticket pool to which tickets will be added.
     * @param ticketReleaseRate The rate at which tickets are released (must be greater than zero).
     * @throws IllegalArgumentException if {@code ticketReleaseRate} is less than or equal to zero.
     */
    public TicketProducer(TicketPool ticketPool, int ticketReleaseRate) {
        if (ticketReleaseRate <= 0) {
            throw new IllegalArgumentException("Ticket release rate must be greater than zero.");
        }
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    /**
     * The run method that continuously produces tickets and adds them to the ticket pool.
     * The producer sleeps for a duration inversely proportional to the ticket release rate
     * to regulate the speed of ticket production.
     *
     * <p>
     * If the thread is interrupted, the production stops, and a message is logged to indicate
     * the interruption. In case of other exceptions, the production terminates with an error message.
     * </p>
     */
    public void run() {
        int ticketCounter = 1;// Tracks the number of tickets produced
        while (true) {
            try {
                ticketPool.addTicket(ticketCounter++); // Add a ticket to the pool
                Thread.sleep(1000 / ticketReleaseRate); // Sleep to control ticket release rate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();// Restore interrupt status
                System.out.println("Producer thread interrupted. Exiting ticket production.");
                break;// Exit the loop on interruption
            } catch (Exception e) {
                System.err.println("Error during ticket production: " + e.getMessage());
                break;// Exit on any other exception

            }
        }
    }
}
