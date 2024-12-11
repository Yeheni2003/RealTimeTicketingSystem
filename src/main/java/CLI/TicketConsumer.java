package CLI;

/**
 * The {@code TicketConsumer} class represents a consumer that retrieves tickets
 * from the {@link TicketPool} at a specified customer retrieval rate.
 *
 * <p>
 * This class implements the {@link Runnable} interface to support concurrent execution.
 * Consumers simulate customers purchasing tickets at a fixed rate and run in a loop
 * to retrieve tickets every few seconds.
 * </p>
 *
 * <p>
 * The consumer periodically retrieves tickets from the shared {@code TicketPool},
 * respecting the configured retrieval rate. If the thread is interrupted,
 * it will gracefully terminate.
 * </p>
 *
 * @see TicketPool
 * @see Runnable
 */
class TicketConsumer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    /**
     * Constructs a {@code TicketConsumer} instance with a reference to the shared
     * {@link TicketPool} and a specified customer retrieval rate.
     *
     * @param ticketPool           The shared {@link TicketPool} from which tickets are retrieved.
     * @param customerRetrievalRate The rate at which tickets are retrieved (number of tickets per interval).
     */
    public TicketConsumer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    /**
     * Starts the ticket consumer's execution loop.
     *
     * <p>
     * The consumer attempts to retrieve a specified number of tickets
     * (equal to {@code customerRetrievalRate}) from the shared {@code TicketPool}
     * every 3 seconds. This simulates a continuous customer ticket retrieval process.
     * </p>
     *
     * <p>
     * The loop continues indefinitely until the thread is interrupted. Upon interruption,
     * the method ensures that the thread terminates cleanly.
     * </p>
     */
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < customerRetrievalRate; i++) {
                    ticketPool.retrieveTickets(customerRetrievalRate);
                }
                Thread.sleep(3000); // Try to purchase tickets every 3 seconds
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
