package org.example.commands;

import org.example.AccountHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.util.CommandUtils.*;

public class AR implements Command {
    private final ConcurrentHashMap<String, Long> accounts;
    private static final Logger logger = LoggerFactory.getLogger(AR.class);

    public AR(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {
        logger.info("Executing AR command with arguments: {}", (Object[]) args);

        Map<String, String> parsedArgs = parseArgs(args[1]);
        String accountNum = parsedArgs.get("accountNum");

        if (!isValidAccountFormat(accountNum)) {
            logger.warn("Invalid account number format: {}", accountNum);
            return "ER Formát čísla účtu není správný.";
        }

        if (!accounts.containsKey(accountNum)) {
            logger.warn("Account not found: {}", accountNum);
            return "ER Účet neexistuje.";
        }

        long balance = accounts.getOrDefault(accountNum, 0L);
        if (balance > 0) {
            logger.warn("Attempted to delete account {} with a positive balance: {}", accountNum, balance);
            return "ER Nelze smazat bankovní účet na kterém jsou finance.";
        }

        accounts.remove(accountNum);
        AccountHandler.saveAccounts(accounts);
        logger.info("Account {} deleted successfully.", accountNum);

        return "AR";
    }
}
