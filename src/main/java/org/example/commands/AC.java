package org.example.commands;

import org.example.AccountHandler;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class AC implements Command {
    private final ConcurrentHashMap<String, Long> accounts;

    public AC(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) {
            return "ER příkaz AC nemá argumenty";
        }

        Random rand = new Random();
        String accountNumber;

        do {
            accountNumber = String.valueOf(rand.nextInt(90000) + 10000);
        } while (accounts.containsKey(accountNumber));

        accounts.put(accountNumber, 0L);
        AccountHandler.saveAccounts(accounts);
        return "AC " + accountNumber;
    }
}
