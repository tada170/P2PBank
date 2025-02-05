package org.example.commands;

import java.util.concurrent.ConcurrentHashMap;

public class BA implements Command {
    private final ConcurrentHashMap<String, Long> accounts;

    public BA(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) {
            return "ER příkaz BA nemá argumenty";
        }
        long total = accounts.values().stream().mapToLong(Long::longValue).sum();

        return "BA " + total;
    }
}
