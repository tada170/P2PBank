package org.example.commands;

import org.example.AccountHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class AC implements Command {
    private static final Logger logger = LoggerFactory.getLogger(AC.class);

    private final ConcurrentHashMap<String, Long> accounts;

    public AC(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {
        logger.info("Executing AC command with arguments: {}", (Object[]) args);

        if (args.length != 1) {
            logger.warn("AC command does not have arguments");
            return "ER příkaz AC nemá argumenty";
        }

        Random rand = new Random();
        String accountNumber;

        do {
            accountNumber = String.valueOf(rand.nextInt(90000) + 10000);
        } while (accounts.containsKey(accountNumber));

        accounts.put(accountNumber, 0L);
        AccountHandler.saveAccounts(accounts);

        logger.info("New account created: {}", accountNumber);
        return "AC " + accountNumber;
    }
}
