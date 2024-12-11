import React, { useState } from "react";

function ConfigurationForm({ onStart }) {
    const [totalTickets, setTotalTickets] = useState(100);
    const [ticketReleaseRate, setTicketReleaseRate] = useState(10);
    const [customerRetrievalRate, setCustomerRetrievalRate] = useState(10);

    const handleSubmit = (e) => {
        e.preventDefault();
        onStart(ticketReleaseRate, customerRetrievalRate);
    };

    return (
        <div className="configuration-form">
            <h2>Configure Ticket System</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Total Tickets</label>
                    <input
                        type="number"
                        value={totalTickets}
                        onChange={(e) => setTotalTickets(e.target.value)}
                    />
                </div>
                <div>
                    <label>Ticket Release Rate</label>
                    <input
                        type="number"
                        value={ticketReleaseRate}
                        onChange={(e) => setTicketReleaseRate(e.target.value)}
                    />
                </div>
                <div>
                    <label>Customer Retrieval Rate</label>
                    <input
                        type="number"
                        value={customerRetrievalRate}
                        onChange={(e) => setCustomerRetrievalRate(e.target.value)}
                    />
                </div>
                <button type="submit">Start System</button>
            </form>
        </div>
    );
}

export default ConfigurationForm;
