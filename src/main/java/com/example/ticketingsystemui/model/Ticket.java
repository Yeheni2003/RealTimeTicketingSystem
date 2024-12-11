package com.example.ticketingsystemui.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code Ticket} class represents a ticket entity in the system.
 *
 * <p> This entity is mapped to a database table using JPA annotations.</p>
 */

@Getter
@Entity
public class Ticket {

    /**
     * The unique identifier for each ticket.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * The status of the ticket,e.g.,"Available" or "Sold".
     */
    @Setter
    private String status;

    /**
     * Default constructor for JPA.
     */
    public Ticket() {}


    /**
     * Constructs a {@code Ticket}
     * @param status the status of the ticket
     */
    public Ticket(String status) {
        this.status = status;
    }

}
