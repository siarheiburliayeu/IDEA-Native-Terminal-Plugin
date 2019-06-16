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

    private static final String PROJECT_DIR_PLACEHOLDER = "${project.dir}";

    public static Command createCommand(@NotNull Environment env,
                                        @NotNull String projectDirectory,
                                        @Nullable String favoriteTerminalString) throws FileNotFoundException {
        checkProjectDirectory(projectDirectory);

        String command = favoriteTerminalString != null
                ? favoriteTerminalString
                : env.getDefaultTerminal().getCommand();

        if (isCustomCommand(command)) {
            String[] customCommand = command
                    .replace(PROJECT_DIR_PLACEHOLDER, projectDirectory)
                    .split(" ");

            return new Command(customCommand);
        }

        Terminal terminal = Terminal.fromString(command);
        log.info("Favorite terminal is [" + favoriteTerminalString + "] and using [" + terminal + "]");

        OperationSystem os = env.getOs();
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

                    case CMDER:
                        return new Command(command, "/task", "cmder", projectDirectory);

                    case GIT_BASH:
                        return new Command(command, "--cd=" + projectDirectory);

                    case BASH:
                        return new Command("cmd", "/k", "start", "/d",
                                projectDirectory.replace("/", "\\"), command);

                    default:
                        return new Command("cmd", "/c", "start", command);
                }

            case LINUX:
                switch (terminal) {
                    case GNOME_TERMINAL:
                        return new Command(command, "--working-directory", projectDirectory);
                    case KONSOLE:
                        return new Command(command, "--workdir", projectDirectory);
                    case RXVT:
                        return new Command(command, "-cd", projectDirectory);

                    default:
                        return new Command(command);
                }

            case MAC_OS:
                switch (terminal) {
                    case MAC_TERMINAL:
                    case I_TERM:
                    default:
                        return new Command("open", projectDirectory, "-a", command);
                }

            default:
                throw new RuntimeException("The environment is not supported: " + os);
        }
    }

    protected static boolean isCustomCommand(String command) {
        return command.contains(PROJECT_DIR_PLACEHOLDER);
    }

    protected static void checkProjectDirectory(@NotNull String projectDirectory) throws FileNotFoundException {
        File path = new File(projectDirectory);
        if (!path.exists()) {
            throw new FileNotFoundException(path.getPath());
        }
        if (!path.isDirectory()) {
            throw new IllegalArgumentException(path.getPath());
        }
    }
}
