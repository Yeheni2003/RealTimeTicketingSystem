package CLI;
import com.google.gson.*;
import java.io.*;
import java.nio.file.*;

/**
 * The {@code Configuration} class is responsible for managing the application's
 * configuration settings. It allows loading and saving configuration data
 * to and from a JSON file.
 *
 * <p>
 * This class uses the {@link com.google.gson.Gson} library to parse and write
 * JSON data. Configuration settings include:
 * <ul>
 *     <li>Total number of tickets</li>
 *     <li>Ticket release rate</li>
 *     <li>Customer retrieval rate</li>
 *     <li>Maximum ticket capacity</li>
 * </ul>
 *
 * <p>Default behavior ensures that if the configuration file does not exist or
 * is invalid, default values are used.</p>
 */
public class Configuration {
    private static final String CONFIG_FILE = "config.json";

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    /**
     * Loads configuration settings from the {@code config.json} file.
     *
     * <p>
     * If the file is not found or invalid, the method falls back to default values.
     * It reads the file content and parses it using the {@link JsonParser} to set
     * the configuration properties.
     * </p>
     *
     * <p>
     * Example JSON format:
     * <pre>
     * {
     *     "totalTickets": 1000,
     *     "ticketReleaseRate": 10,
     *     "customerRetrievalRate": 5,
     *     "maxTicketCapacity": 200
     * }
     * </pre>
     * @throws IOException if the configuration file cannot be read.
     */
    public void loadConfiguration() throws IOException{
        try {
            String json = Files.readString(Path.of(CONFIG_FILE));
            JsonObject config = JsonParser.parseString(json).getAsJsonObject();

            totalTickets = config.get("totalTickets").getAsInt();
            ticketReleaseRate = config.get("ticketReleaseRate").getAsInt();
            customerRetrievalRate = config.get("customerRetrievalRate").getAsInt();
            maxTicketCapacity = config.get("maxTicketCapacity").getAsInt();

            System.out.println("Configuration loaded successfully!");
        } catch (IOException e) {
            System.out.println("No valid config file found. Using default values.");
        }
    }

    /**
     * Saves the current configuration settings to the {@code config.json} file.
     *
     * <p>
     * The configuration settings are serialized into JSON format and
     * written to the file system. The file is formatted with pretty printing
     * for better readability.
     * </p>
     * @throws IOException if the configuration file cannot be written.
     */
    public void saveConfiguration() throws IOException{
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            JsonObject config = new JsonObject();
            config.addProperty("totalTickets", totalTickets);
            config.addProperty("ticketReleaseRate", ticketReleaseRate);
            config.addProperty("customerRetrievalRate", customerRetrievalRate);
            config.addProperty("maxTicketCapacity", maxTicketCapacity);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, writer);

            System.out.println("Configuration saved successfully!");
        } catch (IOException e) {
            System.out.println("Failed to save configuration: " + e.getMessage());
        }
    }

}



