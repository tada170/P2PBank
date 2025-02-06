package org.example;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountHandler {

    public static void saveAccounts(ConcurrentHashMap<String, Long> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv"))) {
            writer.write("Account,Balance");
            writer.newLine();

            for (Map.Entry<String, Long> entry : accounts.entrySet()) {
                String account = entry.getKey();
                Long balance = entry.getValue();
                writer.write(account + "," + balance);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, Long> loadAccounts() {
        ConcurrentHashMap<String, Long> accounts = new ConcurrentHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String account = parts[0].trim();
                    try {
                        Long balance = Long.parseLong(parts[1].trim());
                        accounts.put(account, balance);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
