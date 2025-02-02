package org.example.commands;


public class BC implements Command{
    private final String host;
    public BC(String host) {
        this.host = host;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) {
            return "ER příkaz BC nemá argumenty";
        }
        return "BC " + host;
    }
}
