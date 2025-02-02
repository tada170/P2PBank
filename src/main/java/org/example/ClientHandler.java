package org.example;

import org.example.commands.CommandRegistry;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final CommandRegistry registry;

    public ClientHandler(Socket socket, CommandRegistry registry) {
        this.clientSocket = socket;
        this.registry = registry;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String input;
            while ((input = in.readLine()) != null) {
                String response = registry.executeCommand(input);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
