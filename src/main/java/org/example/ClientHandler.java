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
                try {
                    String response = registry.executeCommand(input);
                    out.println(response);
                } catch (IllegalArgumentException e) {
                    out.println("ER Neplatný formát příkazu: " + e.getMessage());
                } catch (Exception e) {
                    out.println("ER Interní chyba serveru.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Chyba při komunikaci s klientem: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Chyba při uzavírání socketu klienta: " + e.getMessage());
            }
        }
    }
}
