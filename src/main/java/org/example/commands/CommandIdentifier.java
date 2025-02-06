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

    /**
     * This method converts a string representation of a command identifier into its corresponding enum value.
     *
     * @param value The string representation of the command identifier.
     * @return The corresponding enum value if found, otherwise null.
     */
    public static CommandIdentifier fromString(String value) {
        for (CommandIdentifier commandIdentifier : CommandIdentifier.values()) {
            if (commandIdentifier.name().equals(value)) {
                return commandIdentifier;
            }
        }
        return null;
    }
}
