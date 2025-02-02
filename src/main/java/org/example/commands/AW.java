package org.example.commands;

import org.example.AccountHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.util.CommandUtils.*;

public class AW implements Command {
    private final ConcurrentHashMap<String, Long> accounts;

    public AW(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {
        Map<String, String> parsedArgs = parseArgs(args[1]);
        String accountNum = parsedArgs.get("accountNum");
        String accountAmount = parsedArgs.get("amount");

        if (!isValidAccountFormat(accountNum)) {
            return "ER Formát čísla účtu není správný.";
        }

        if (!accounts.containsKey(accountNum)) {
            return "ER Účet neexistuje.";
        }

        if (!isValidAmount(accountAmount)) {
            return "ER Částka není ve správném formátu.";
        }

        long amount = Long.parseLong(accountAmount);
        long currentBalance = accounts.getOrDefault(accountNum, 0L);

        if (currentBalance < amount) {
            return "ER Není dostatek finančních prostředků.";
        }

        accounts.put(accountNum, currentBalance - amount);
        AccountHandler.saveAccounts(accounts);
        return "AW";
    }

}
