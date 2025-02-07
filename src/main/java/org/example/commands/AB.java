package org.example.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.Client.sendCommand;
import static org.example.util.CommandUtils.*;

public class AB implements Command {
    private static final Logger logger = LoggerFactory.getLogger(AB.class);

    private final ConcurrentHashMap<String, Long> accounts;
    private final String hostIp;

    public AB(ConcurrentHashMap<String, Long> accounts, String hostIp) {
        this.accounts = accounts;
        this.hostIp = hostIp;
    }

    @Override
    public String execute(String[] args) {
        logger.info("Executing AB command with arguments: {}", (Object[]) args);

        if (args.length < 2 ||!isValidCommand(args[1])) {
            logger.warn("AB command received with invalid arguments: {}", (Object[]) args);
            return "ER příkaz není zadán správně.";
        }

        Map<String, String> parsedArgs = parseArgs(args[1]);
        String accountNum = parsedArgs.get("accountNum");
        String accountIp = parsedArgs.get("accountIp");

        if (!isValidIp(accountIp)) {
            logger.warn("Invalid IP address provided: {}", accountIp);
            return "ER IP adresa není správná.";
        }

        if (!accountIp.equals(hostIp)) {
            String command = args[0] + " " + args[1];
            logger.info("Forwarding command to another server: {}", command);
            return sendCommand(command);
        }

        if (!isValidAccountFormat(accountNum)) {
            logger.warn("Invalid account number format: {}", accountNum);
            return "ER Formát čísla účtu není správný.";
        }

        if (!accounts.containsKey(accountNum)) {
            logger.warn("Account not found: {}", accountNum);
            return "ER Účet neexistuje.";
        }

        long balance = accounts.getOrDefault(accountNum, 0L);
        logger.info("Account balance for {}: {}", accountNum, balance);

        return "AB " + balance;
    }

}
