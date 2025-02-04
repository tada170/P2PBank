package org.example.commands;

import java.util.HashMap;
import java.util.Map;
import static org.example.commands.CommandIdentifier.fromString;

public class CommandRegistry {
    private final Map<CommandIdentifier, Command> commands = new HashMap<>();

    public void registerCommand(CommandIdentifier name, Command command) {
        commands.put(name, command);
    }

    public String executeCommand(String input) {
        String[] parts = input.split(" ", 2);
        CommandIdentifier commandName = fromString(parts[0]);

        if (commandName == null) {
            return "ER Neznámý příkaz.";
        }

        Command command = commands.get(commandName);
        if (command == null) {
            return "ER Neznámý příkaz.";
        }

        return command.execute(parts);
    }

}
