package org.example.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class BN implements Command {
    private final ConcurrentHashMap<String, Long> accounts;
    private static final Logger logger = LoggerFactory.getLogger(BN.class);

    public BN(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {
        logger.info("Executing BN command with arguments: {}", (Object[]) args);

        if (args.length != 1) {
            logger.warn("BN command received with incorrect number of arguments. Expected 1, got {}", args.length);
            return "ER příkaz BN nemá argumenty";
        }

        int clientCount = accounts.size();
        logger.info("Client count: {}", clientCount);

        return "BN " + clientCount;
    }
}
