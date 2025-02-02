package org.example.commands;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.util.CommandUtils.*;

public class AB implements Command {
    private final ConcurrentHashMap<String, Long> accounts;

    public AB(ConcurrentHashMap<String, Long> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String execute(String[] args) {

        Map<String, String> parsedArgs = parseArgs(args[1]);
        String accountNum = parsedArgs.get("accountNum");

        if (!isValidAccountFormat(accountNum)) {
            return "ER Formát čísla účtu není správný.";
        }
        if (!accounts.containsKey(accountNum)) {
            return "ER Účet neexistuje.";
        }
        long balance = accounts.getOrDefault(accountNum, 0L);

        return "AB " + balance;
    }

    private boolean isValidAccountFormat(String account) {
        return account.matches("\\d{5}");
    }
}
