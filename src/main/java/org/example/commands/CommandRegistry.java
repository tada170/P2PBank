package org.example.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import static org.example.commands.CommandIdentifier.fromString;

public class CommandRegistry {
    private final Map<CommandIdentifier, Command> commands = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CommandRegistry.class);

    public void registerCommand(CommandIdentifier name, Command command) {
        commands.put(name, command);
        logger.info("Command registered: {}", name);
    }

    public String executeCommand(String input) {
        String[] parts = input.split(" ", 2);
        CommandIdentifier commandName = fromString(parts[0]);

        if (commandName == null) {
            logger.warn("Received unknown command: {}", parts[0]);
            return "ER Neznámý příkaz.";
        }

        Command command = commands.get(commandName);
        if (command == null) {
            logger.error("Command not found in registry: {}", commandName);
            return "ER Neznámý příkaz.";
        }

        logger.info("Executing command: {}", commandName);
        return command.execute(parts);
    }
}
