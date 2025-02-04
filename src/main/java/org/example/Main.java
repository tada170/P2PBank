package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.commands.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.example.AccountHandler.loadAccounts;

public class Main {
    private static String HOST;
    private static int PORT;
    private static int POOL_SIZE;

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        HOST = dotenv.get("HOST", "127.0.0.1");
        PORT = Integer.parseInt(dotenv.get("PORT", "65530"));
        POOL_SIZE = Integer.parseInt(dotenv.get("POOL_SIZE", "4"));

        ExecutorService threadPool = Executors.newFixedThreadPool(POOL_SIZE);

        ConcurrentHashMap<String, Long> accounts = loadAccounts();

        CommandRegistry registry = new CommandRegistry();
        registry.registerCommand(CommandIdentifier.BC, new BC(HOST));
        registry.registerCommand(CommandIdentifier.AC, new AC(accounts));
        registry.registerCommand(CommandIdentifier.AD, new AD(accounts,HOST));
        registry.registerCommand(CommandIdentifier.AW, new AW(accounts));
        registry.registerCommand(CommandIdentifier.AB, new AB(accounts));
        registry.registerCommand(CommandIdentifier.AR, new AR(accounts));
        registry.registerCommand(CommandIdentifier.BA, new BA(accounts));
        registry.registerCommand(CommandIdentifier.BN, new BN(accounts));

        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(HOST))) {
            System.out.println("TCP server běží na " + HOST + ":" + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Klient se pripojil");
                threadPool.submit(new ClientHandler(clientSocket, registry));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
