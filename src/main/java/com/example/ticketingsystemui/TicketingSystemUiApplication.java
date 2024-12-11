package com.example.ticketingsystemui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@code TicketingSystemUiApplication} class serves as the entry point for the Spring Boot
 * application. It initializes and runs the backend for the Ticketing System.
 *
 * <p>
 * This class is annotated with {@link SpringBootApplication}, which enables:
 * <ul>
 *     <li>Component scanning</li>
 *     <li>Auto-configuration</li>
 *     <li>Spring Boot application context setup</li>
 * </ul>
 *
 * The {@code main} method uses the {@link SpringApplication#run} method to start the application
 * and load the Spring context.
 */

@SpringBootApplication
public class TicketingSystemUiApplication {

    /**
     * The main method that serves as the application's entry point.
     *
     * <p>
     * It launches the Spring Boot application and prints a confirmation message to
     * indicate that the backend is running.
     * </p>
     *
     * @param args command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(TicketingSystemUiApplication.class, args);
        System.out.println("Ticket System Backend is running...");
    }

}
