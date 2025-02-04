package org.example.commands;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.Client.connectAndSendMessage;
import static org.example.util.CommandUtils.*;

public class AB implements Command {
    private final ConcurrentHashMap<String, Long> accounts;
    private final String hostIp;
    public AB(ConcurrentHashMap<String, Long> accounts, String hostIp) {
        this.accounts = accounts;
        this.hostIp = hostIp;
    }

    @Override
    public String execute(String[] args) {

        Map<String, String> parsedArgs = parseArgs(args[1]);
        String accountNum = parsedArgs.get("accountNum");
        String accountIp = parsedArgs.get("accountIp");

        if (!accountIp.equals(hostIp)){
            String command = args[0] +" "+ args[1];
            return connectAndSendMessage(command);
        }
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
