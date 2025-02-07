package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * This class represents a client that sends commands to a remote server.
 * The client connects to a specified IP address and port range, sends a command,
 * and waits for a response. If no response is received within a specified time-out,
 * the client continues to the next port.
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    /**
     * Sends a command to a remote server.
     *
     * @param command The command to be sent. The command should be in the format:
     *                "command/ip_address command_parameters".
     * @return The response received from the server. If the server does not exist,
     *         returns "ER Bank does not exist".
     */
    public static String sendCommand(String command) {
        Dotenv dotenv = Dotenv.load();
        logger.info("Sending command: {}", command);

        String[] parts = command.split("/");
        String ipAddress = parts[1].split(" ")[0];
        int time_out = Integer.parseInt(dotenv.get("S_TIME_OUT", "1000"));

        for (int port = 65525; port <= 65535; port++) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), time_out);
                logger.info("Connected to {} on port {}", ipAddress, port);

                try (OutputStream output = socket.getOutputStream();
                     PrintWriter writer = new PrintWriter(output, true);
                     InputStream input = socket.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

                    writer.println(command);
                    logger.info("Command sent: {}", command);

                    String response = reader.readLine();
                    if (response != null) {
                        logger.info("Response received: {}", response);
                        return response;
                    }
                }
            } catch (IOException e) {
                logger.warn("Failed to connect to {} on port {}: {}", ipAddress, port, e.getMessage());
            }
        }
        logger.error("ER Bank does not exist");
        return "ER Bank does not exist";
    }
}
