package org.example.util;

import java.util.HashMap;
import java.util.Map;

public class CommandUtils {
    public static boolean isValidAccountFormat(String account) {
        return account.matches("\\d{5}");
    }
    public  static boolean isValidCommand(String input){
        String[] parts = input.split("[/\\s]+");
        return  parts.length >= 2 && parts.length <= 3;
    }
    public static Map<String, String> parseArgs(String input) {
        String[] parts = input.split("[/\\s]+");
        String accountNum = parts[0];
        String ipAddress = parts[1];
        Map<String, String> result = new HashMap<>();
        result.put("accountNum", accountNum);
        result.put("accountIp", ipAddress);
        result.put("amount", parts.length == 3 ? parts[2] : null);

        return result;
    }

    public static boolean isValidIp(String ip) {
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        for (String part : parts) {
            try {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidAmount(String amountStr) {
        try {
            long amount = Long.parseLong(amountStr);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
