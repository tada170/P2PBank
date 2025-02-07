package org.example.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

import static org.example.util.CommandUtils.isValidCommand;

public class BA implements Command {
    private final ConcurrentHashMap<String, Long> accounts;
    private static final Logger logger = LoggerFactory.getLogger(BA.class);

    public BA(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {
        logger.info("Executing BA command with arguments: {}", (Object[]) args);

        if (args.length != 1) {
            logger.warn("BA command received with incorrect number of arguments. Expected 1, got {}", args.length);
            return "ER příkaz BA nemá argumenty";
        }

        long total = accounts.values().stream().mapToLong(Long::longValue).sum();
        logger.info("Total account balance: {}", total);

        return "BA " + total;
    }
}
