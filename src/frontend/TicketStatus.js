import React from "react";

function TicketStatus({ availableTickets }) {
    return (
        <div className="ticket-status">
            <h2>Available Tickets: {availableTickets}</h2>
        </div>
    );
}

export default TicketStatus;
