package org.example;

import org.example.commands.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * This class represents a handler for client connections in a P2P banking application.
 * It implements the Runnable interface to handle incoming client requests in a separate thread.
 */
public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    private final CommandRegistry registry;

    /**
     * Constructs a new ClientHandler instance.
     *
     * @param socket The socket representing the client connection.
     * @param registry The command registry to execute client commands.
     */
    public ClientHandler(Socket socket, CommandRegistry registry) {
        this.clientSocket = socket;
        this.registry = registry;
    }

    /**
     * Handles the client connection by reading commands, executing them, and sending responses.
     * Logs relevant information and errors.
     */
    @Override
    public void run() {
        logger.info("New connection from: {}", clientSocket);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String input;
            while ((input = in.readLine()) != null) {
                input = input.trim();
                if (input.isEmpty()) {
                    continue;
                }

                logger.info("Received command: {}", input);
                try {
                    String response = registry.executeCommand(input);
                    if (response != null && !response.isEmpty()) {
                        out.println(response);
                        logger.info("Response to client: {}", response);
                    }
                } catch (IllegalArgumentException e) {
                    String errorMsg = "ER Invalid command format: " + e.getMessage();
                    out.println(errorMsg);
                    logger.warn("Invalid command format: {}", e.getMessage());
                } catch (Exception e) {
                    out.println("ER Internal server error.");
                    logger.error("Internal server error while processing command.", e);
                }
            }
        } catch (IOException e) {
            logger.error("Communication error with client: {}", e.getMessage());
        } finally {
            try {
                clientSocket.close();
                logger.info("Client disconnected: {}", clientSocket);
            } catch (IOException e) {
                logger.error("Error while closing client socket: {}", e.getMessage());
            }
        }
    }
}
