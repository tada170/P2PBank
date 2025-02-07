package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.commands.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    private final CommandRegistry registry;

    public ClientHandler(Socket socket, CommandRegistry registry) {
        this.clientSocket = socket;
        this.registry = registry;
    }

    @Override
    public void run() {
        logger.info("New connection from: {}", clientSocket);
        Dotenv dotenv = Dotenv.load();
        int time_out = Integer.parseInt(dotenv.get("C_TIME_OUT", "5000"));
        try {
            clientSocket.setSoTimeout(time_out);
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
                        printToClient(clientSocket, errorMsg);
                        logger.warn("Invalid command format: {}", e.getMessage());
                    } catch (Exception e) {
                        printToClient(clientSocket, "ER Internal server error.");
                        logger.error("Internal server error while processing command.", e);
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            logger.warn("Client timed out: {}", clientSocket);
            printToClient(clientSocket,"ER Cas vyprsel");
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

    private void printToClient(Socket socket, String message)
    {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(message);
        } catch (IOException e) {
            logger.error("Error while sending message to client: {}", e.getMessage());
        }
    }
}
