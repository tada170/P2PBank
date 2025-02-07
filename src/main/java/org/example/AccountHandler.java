package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class provides methods for handling bank accounts.
 */
public class AccountHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccountHandler.class);

    /**
     * Saves the given accounts to a CSV file named "accounts.csv".
     *
     * @param accounts A map containing account numbers as keys and their respective balances as values.
     */
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
            logger.info("Successfully saved accounts to file.");
        } catch (IOException e) {
            logger.error("Error while saving accounts: ", e);
        }
    }

    /**
     * Loads accounts from a CSV file named "accounts.csv" and returns them as a map.
     *
     * @return A map containing account numbers as keys and their respective balances as values.
     */
    public static ConcurrentHashMap<String, Long> loadAccounts() {
        ConcurrentHashMap<String, Long> accounts = new ConcurrentHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.csv"))) {
            String line;
            reader.readLine();  // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String account = parts[0].trim();
                    try {
                        Long balance = Long.parseLong(parts[1].trim());
                        accounts.put(account, balance);
                    } catch (NumberFormatException e) {
                        logger.warn("Skipping invalid line: {}", line);
                    }
                }
            }
            logger.info("Successfully loaded accounts from file.");
        } catch (IOException e) {
            logger.error("Error while loading accounts: ", e);
        }
        return accounts;
    }
}
