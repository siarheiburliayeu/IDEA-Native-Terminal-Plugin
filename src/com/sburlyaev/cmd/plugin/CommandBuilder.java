package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.diagnostic.Logger;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;
import com.sburlyaev.cmd.plugin.model.Terminal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;

public class CommandBuilder {

    private static final Logger log = Logger.getInstance(CommandBuilder.class);

    public static Command createCommand(@NotNull Environment env,
                                        @NotNull String projectDirectory,
                                        @Nullable String favoriteTerminalString) throws FileNotFoundException {
        if (!new File(projectDirectory).exists()) {
            throw new FileNotFoundException(projectDirectory);
        }
        OperationSystem os = env.getOs();

        String command = favoriteTerminalString == null
                ? os.getDefaultTerminal().getCommand()
                : favoriteTerminalString;
        Terminal terminal = Terminal.fromString(command);
        log.info("Favorite terminal is [" + favoriteTerminalString + "] and using [" + terminal + "]");

        StringBuilder sb = new StringBuilder();
        switch (os) {
            case WINDOWS:

                switch (terminal) {
                    case COMMAND_PROMPT:
                        return new Command("cmd", "/c", "start", command, "/K", "cd", "/d", projectDirectory);

                    case POWER_SHELL:
                        return new Command("cmd", "/c", "start", command, "-NoExit", "-Command",
                                "Set-Location", "'" + projectDirectory + "'");

                    case CON_EMU:
                        String conEmuRunCommand = " -run ";
                        String[] commands = command.split(conEmuRunCommand);
                        Command executableCommand = new Command(commands[0], "-Dir", projectDirectory);

                        if (commands.length == 2) {
                            executableCommand.add("-run", commands[1]);
                        }
                        return executableCommand;

                    case GIT_BASH:
                        sb.append("\"")
                                .append(command)
                                .append("\"")
                                .append(" --cd=")
                                .append("\"")
                                .append(projectDirectory)
                                .append("\"");
                        break;

                    default:
                        sb.append(command);
                        break;
                }
                break;

            case LINUX:

                switch (terminal) {
                    case GNOME_TERMINAL:
                        return new Command(command, "--working-directory", projectDirectory);

                    default:
                        sb.append(command);
                        break;
                }
                break;

            case MAC_OS:  // Terminal, iTerm
                return new Command("open", projectDirectory, "-a", command);

            default:
                throw new RuntimeException("The environment is not supported: " + os);
        }
        return new Command(sb.toString());
    }
}
