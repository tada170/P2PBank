package org.example.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BC implements Command {
    private final String host;
    private static final Logger logger = LoggerFactory.getLogger(BC.class);

    public BC(String host) {
        this.host = host;
    }

    @Override
    public String execute(String[] args) {
        logger.info("Executing BC command with arguments: {}", (Object[]) args);

        if (args.length != 1) {
            logger.warn("BC command received with incorrect number of arguments. Expected 1, got {}", args.length);
            return "ER příkaz BC nemá argumenty";
        }

        logger.info("Returning host: {}", host);
        return "BC " + host;
    }
}
