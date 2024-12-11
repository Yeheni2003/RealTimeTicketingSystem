package com.example.ticketingsystemui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The {@code CorsConfig} class is a configuration class for enabling Cross-Origin Resource Sharing (CORS)
 * in a Spring Boot application. This configuration allows the backend API to interact
 * with a frontend application hosted on a different origin.
 *
 * <p>
 * This configuration is particularly useful for enabling communication between the
 * Spring Boot backend and a React frontend running locally on a separate port.
 * </p>
 *
 * <p>
 * CORS settings such as allowed origins, HTTP methods, headers, and credentials are
 * configured using {@link CorsRegistry}.
 * </p>
 */
@Configuration
public class CorsConfig {

    /**
     * Configures CORS settings for the application.
     *
     * <p>
     * This method defines a {@link WebMvcConfigurer} bean that customizes the CORS mapping
     * for all endpoints. The configuration allows the backend to accept requests from
     * a specific frontend origin (e.g., {@code http://localhost:3000}).
     * </p>
     *
     * @return a {@link WebMvcConfigurer} with the customized CORS settings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * Configures CORS mappings to allow specific origins, methods, and headers
             * for cross-origin requests.
             *
             * <p>
             * This method applies the following settings:
             * <ul>
             *     <li>Allows all paths ({@code /**}) to accept cross-origin requests.</li>
             *     <li>Permits requests from the React frontend origin: {@code http://localhost:3000}.</li>
             *     <li>Allows HTTP methods: GET, POST, PUT, DELETE, and OPTIONS.</li>
             *     <li>Permits all HTTP headers.</li>
             *     <li>Allows credentials, such as cookies, to be included in the requests.</li>
             * </ul>
             * </p>
             *
             * @param registry the {@link CorsRegistry} to configure CORS mappings.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply CORS to all endpoints
                        .allowedOrigins("http://localhost:3000") // React frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow cookies if needed
            }
        };
    }
}
