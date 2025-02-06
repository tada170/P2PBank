package org.example.commands;

import java.util.concurrent.ConcurrentHashMap;

public class BN implements Command {
    private final ConcurrentHashMap<String, Long> accounts;

    public BN(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) {
            return "ER příkaz BN nemá argumenty";
        }
        int clientCount = accounts.size();

        return "BN " + clientCount;
    }
}
