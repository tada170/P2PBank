package org.example.commands;

import org.example.AccountHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.Client.sendCommand;
import static org.example.util.CommandUtils.*;

public class AW implements Command {
    private final ConcurrentHashMap<String, Long> accounts;
    private final String hostIp;
    private static final Logger logger = LoggerFactory.getLogger(AW.class);

    public AW(ConcurrentHashMap<String, Long> accounts, String hostIp) {
        this.accounts = accounts;
        this.hostIp = hostIp;
    }

    @Override
    public String execute(String[] args) {
        logger.info("Executing AW command with arguments: {}", (Object[]) args);

        if (args.length < 2 || !isValidCommand(args[1])) {
            logger.warn("AW command received with invalid arguments: {}", (Object[]) args);
            return "ER příkaz není zadán správně.";
        }

        Map<String, String> parsedArgs = parseArgs(args[1]);
        String accountNum = parsedArgs.get("accountNum");
        String accountAmount = parsedArgs.get("amount");
        String accountIp = parsedArgs.get("accountIp");

        if (!isValidIp(accountIp)) {
            logger.warn("Invalid IP address received: {}", accountIp);
            return "ER IP adresa není správná.";
        }

        if (!accountIp.equals(hostIp)) {
            String command = args[0] + " " + args[1];
            logger.info("Redirecting command to different IP: {}", accountIp);
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

        if (!isValidAmount(accountAmount)) {
            logger.warn("Invalid amount format: {}", accountAmount);
            return "ER Částka není ve správném formátu.";
        }

        long amount = Long.parseLong(accountAmount);
        long currentBalance = accounts.getOrDefault(accountNum, 0L);

        if (currentBalance < amount) {
            logger.warn("Insufficient funds for account {}. Current balance: {}, requested amount: {}", accountNum, currentBalance, amount);
            return "ER Není dostatek finančních prostředků.";
        }

        accounts.put(accountNum, currentBalance - amount);
        AccountHandler.saveAccounts(accounts);
        logger.info("Transaction successful for account {}. New balance: {}", accountNum, accounts.get(accountNum));

        return "AW";
    }
}
