package org.example.commands;

import org.example.AccountHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.util.CommandUtils.*;

public class AR implements Command {
    private final ConcurrentHashMap<String, Long> accounts;

    public AR(ConcurrentHashMap<String, Long> accounts) {
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

        if (balance > 0) {
            return "ER Nelze smazat bankovní účet na kterém jsou finance.";
        }

        accounts.remove(accountNum);
        AccountHandler.saveAccounts(accounts);
        return "AR";
    }
}
