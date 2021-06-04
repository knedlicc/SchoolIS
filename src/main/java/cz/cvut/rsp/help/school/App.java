package cz.cvut.rsp.help.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point of a Spring Boot application.
 * <p>
 * Notice that it is structured as a regular command-line Java application - it has a {@code main} method.
 * <p>
 * The {@link SpringBootApplication} annotation enables auto-configuration of the Spring context. {@link
 * SpringApplication} then starts the Spring context and the whole application.
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
