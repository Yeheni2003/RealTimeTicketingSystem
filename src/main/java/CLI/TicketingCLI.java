package CLI;
import java.util.Scanner;
import com.google.gson.*;
import java.io.*;
import java.nio.file.*;


/**
 * The {@code TicketingCLI} class provides a command-line interface (CLI) for configuring
 * and running a ticketing system. It supports dynamic input of configuration parameters,
 * saving/loading configurations to/from a JSON file, and starting/stopping ticket producer
 * and consumer threads.
 *
 * <p>
 * The system uses a {@link TicketProducer} to simulate ticket releases into a {@link TicketPool},
 * and a {@link TicketConsumer} to simulate customers retrieving tickets from the pool.
 * </p>
 *
 * <p>
 * The user can interact with the system through a console to configure settings,
 * start ticket processing, and stop the system when needed.
 * </p>
 *
 * @see TicketProducer
 * @see TicketConsumer
 * @see TicketPool
 */
public class TicketingCLI {
    private int totalTickets;
    private int ticketsReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private boolean running = true; // Control for stop/start


    public static void main(String[] args) {
        TicketingCLI cli = new TicketingCLI();
        cli.loadConfiguration();
        cli.configureSystem();
    }

    /**
     * Loads configuration parameters from a JSON file named {@code config.json}.
     * <p>
     * If the file is missing or invalid, default values are loaded, and the user is
     * prompted to configure the system manually.
     * </p>
     */
    public void loadConfiguration() {
        try {
            String json = Files.readString(Path.of("config.json"));
            JsonObject config = JsonParser.parseString(json).getAsJsonObject();
            totalTickets = config.get("totalTickets").getAsInt();
            ticketsReleaseRate = config.get("ticketReleaseRate").getAsInt();
            customerRetrievalRate = config.get("customerRetrievalRate").getAsInt();
            maxTicketCapacity = config.get("maxTicketCapacity").getAsInt();

            System.out.println("Configuration loaded from JSON!");
        } catch (IOException e) {
            System.out.println("No valid config file found. Please configure the system.");
            // Default values
            totalTickets = 100;
            ticketsReleaseRate = 10;
            customerRetrievalRate = 10;
            maxTicketCapacity = 50;
        }
    }

    /**
     * Saves the current configuration parameters to a JSON file named {@code config.json}.
     */
    public void saveConfiguration() {
        try (FileWriter writer = new FileWriter("config.json")) {
            JsonObject config = new JsonObject();
            config.addProperty("totalTickets", totalTickets);
            config.addProperty("ticketReleaseRate", ticketsReleaseRate);
            config.addProperty("customerRetrievalRate", customerRetrievalRate);
            config.addProperty("maxTicketCapacity", maxTicketCapacity);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, writer);
            System.out.println("Configuration saved to JSON!");
        } catch (IOException e) {
            System.out.println("Failed to save configuration: " + e.getMessage());
        }
    }

    /**
     * Configures the ticketing system by prompting the user to input parameters such as:
     * <ul>
     *   <li>Total Tickets</li>
     *   <li>Ticket Release Rate</li>
     *   <li>Customer Retrieval Rate</li>
     *   <li>Maximum Ticket Capacity</li>
     * </ul>
     * The configuration is then saved to the JSON file, and the user can start the system.
     */
    public void configureSystem() {
        Scanner scanner = new Scanner(System.in);

        // Prompt for Total Tickets
        System.out.print("Enter Total Tickets: ");
        totalTickets = getValidatedInput(scanner);

        // Prompt for Ticket Release Rate
        System.out.print("Enter Ticket Release Rate: ");
        ticketsReleaseRate = getValidatedInput(scanner);

        // Prompt for Customer Retrieval Rate
        System.out.print("Enter Customer Retrieval Rate: ");
        customerRetrievalRate = getValidatedInput(scanner);

        // Prompt for Max Ticket Capacity
        System.out.print("Enter Max Ticket Capacity: ");
        maxTicketCapacity = getValidatedInput(scanner);

        saveConfiguration();

        System.out.println("System configured! Type 'start' to begin or 'stop' to end.");
        startStopCommand(scanner);
    }

    /**
     * Validates user input to ensure a positive integer is entered.
     *
     * @param scanner The {@link Scanner} object to read user input.
     * @return A valid positive integer input provided by the user.
     */
    private int getValidatedInput(Scanner scanner) {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value <= 0) {
                    System.out.print("Invalid input, please enter a positive number: ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input, please enter a number: ");
            }
        }
        return value;
    }

    /**
     * Controls the start and stop commands of the ticket producer and consumer threads.
     *
     * <p>
     * The producer adds tickets to the {@code TicketPool}, while the consumer retrieves
     * tickets from the pool. Users can issue 'start' or 'stop' commands to control the system.
     * </p>
     *
     * @param scanner The {@link Scanner} object to read user commands.
     */
    private void startStopCommand(Scanner scanner) {
        TicketPool ticketPool = new TicketPool(maxTicketCapacity);
        Thread producerThread = new Thread(new TicketProducer(ticketPool, ticketsReleaseRate));
        Thread consumerThread = new Thread(new TicketConsumer(ticketPool, customerRetrievalRate));

        while (running) {
            System.out.print("Enter command ('start' to begin or 'stop' to end): ");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("start")) {
                if (!producerThread.isAlive() && !consumerThread.isAlive()) {
                    producerThread.start();
                    consumerThread.start();
                    System.out.println("Ticket handling started...");
                } else {
                    System.out.println("System already running.");
                }
            } else if (command.equals("stop")) {
                producerThread.interrupt();
                consumerThread.interrupt();
                System.out.println("Stopping system...");
                running = false;
                try {
                    producerThread.join();
                    consumerThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println("Unknown command. Try 'start' or 'stop'.");
            }
        }
        scanner.close();
    }
}
