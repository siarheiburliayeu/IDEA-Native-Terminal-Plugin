package com.sburlyaev.cmd.plugin.model;

import java.io.IOException;

public class Command {

    private final String command;

    public Command(String command) {
        this.command = command;
    }

    public void execute() throws IOException {
        Runtime.getRuntime().exec(command);
    }

    public String getCommand() {
        return command;
    }
}
