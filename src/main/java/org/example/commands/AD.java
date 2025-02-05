package org.example.commands;

import org.example.AccountHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.Client.sendCommand;
import static org.example.util.CommandUtils.*;

public class AD implements Command {
    private final ConcurrentHashMap<String, Long> accounts;
    private final String hostIp;

    public AD(ConcurrentHashMap<String, Long> accounts, String hostIp) {
        this.accounts = accounts;
        this.hostIp = hostIp;
    }

    @Override
    public String execute(String[] args) {
        if(!isValidCommand(args[1])){
            return "ER příkaz není zadán správně.";
        }
        Map<String, String> parsedArgs = parseArgs(args[1]);

        String accountNum = parsedArgs.get("accountNum");
        String accountAmount = parsedArgs.get("amount");
        String accountIp = parsedArgs.get("accountIp");

        if (!isValidIp(accountIp)){
            return "ER IP adresa není správná.";
        }

        if (!accountIp.equals(hostIp)){
            String command = args[0] +" "+ args[1];
            return sendCommand(command);
        }

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

        if (amount > 0 && currentBalance > Long.MAX_VALUE - amount) {
            return "ER Částka je příliš velká";
        }
        accounts.put(accountNum, currentBalance + amount);
        AccountHandler.saveAccounts(accounts);

        return "AD";
    }



}
