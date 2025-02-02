package org.example.commands;

public enum CommandIdentifier {
    AC,
    BC,
    AD,
    AW,
    AB,
    AR,
    BA,
    BN;

    public static CommandIdentifier fromString(String value){
        for (CommandIdentifier commandIdentifier : CommandIdentifier.values()){
            if (commandIdentifier.name().equals(value)){
                return commandIdentifier;
            }
        }
        return null;
    }
}
