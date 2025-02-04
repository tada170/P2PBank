package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static String connectAndSendMessage(String command) {
        System.out.println(command);
        String[] parts = command.split("/");
        String ipAddress = parts[1].split(" ")[0];
        for (int port = 65525; port <= 65535; port++) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), 100);

                try (OutputStream output = socket.getOutputStream();
                     PrintWriter writer = new PrintWriter(output, true);
                     InputStream input = socket.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

                    System.out.println("Připojeno k " + ipAddress + " na portu " + port);

                    writer.println(command);
                    System.out.println("Zpráva odeslána.");

                    String response = reader.readLine();
                    if (response != null) {
                        System.out.println("Odpověď serveru: " + response);
                        return response;
                    }
                }
            } catch (IOException e) {
                System.err.println("Nepodařilo se připojit k " + ipAddress + " na port " + port + ": " + e.getMessage());
            }
        }
        return "ER Banka neexistuje";
    }
}