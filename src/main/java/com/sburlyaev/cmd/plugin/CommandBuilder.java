package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.diagnostic.Logger;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;
import com.sburlyaev.cmd.plugin.model.Terminal;
import java.io.File;
import java.io.FileNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandBuilder {

    private static final Logger log = Logger.getInstance(CommandBuilder.class);

    private static final String PROJECT_DIR_PLACEHOLDER = "${project.dir}";

    private CommandBuilder() {
    }

    // refactor it to reduce complexity
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
        log.info("Project directory: " + projectDirectory);

        OperationSystem os = env.os();
        switch (os) {

            case WINDOWS -> {
                switch (terminal) {
                    case COMMAND_PROMPT -> {
                        return new Command("cmd", "/c", "start", command, "/K", "cd", "/d", projectDirectory);
                    }
                    case POWER_SHELL -> {
                        return new Command("cmd", "/c", "start", command, "-NoExit", "-Command",
                            "Set-Location", "'" + projectDirectory + "'");
                    }
                    case WINDOWS_TERMINAL -> {
                        return new Command(command, "-d", projectDirectory);
                    }
                    case CON_EMU -> {
                        String conEmuRunCommand = " -run ";
                        String[] commands = command.split(conEmuRunCommand);
                        Command executableCommand = new Command(commands[0], "-Dir", projectDirectory);
                        if (commands.length == 2) {
                            executableCommand.add("-run", commands[1]);
                        }
                        return executableCommand;
                    }
                    case CMDER -> {
                        return new Command(command, "/task", "cmder", projectDirectory);
                    }
                    case GIT_BASH -> {
                        return new Command(command, "--cd=" + projectDirectory);
                    }
                    case BASH, WSL -> {
                        return new Command("cmd", "/k", "start", "/d",
                            projectDirectory.replace("/", "\\"), command);
                    }
                    default -> {
                        return new Command("cmd", "/c", "start", command);
                    }
                }
            }

            case LINUX -> {
                return switch (terminal) {
                    case GNOME_TERMINAL -> new Command(command, "--working-directory", projectDirectory);
                    case KONSOLE -> new Command(command, "--new-tab", "--workdir", projectDirectory);
                    case TERMINATOR -> new Command(command, "--new-tab", "--working-directory", projectDirectory);
                    case KITTY -> new Command(command, "-1", "-d", projectDirectory);
                    case RXVT -> new Command(command, "-cd", projectDirectory);
                    default -> new Command(command);
                };
            }

            case MAC_OS -> {
                //noinspection SwitchStatementWithTooFewBranches
                return switch (terminal) {
                    case ALACRITTY ->
                        new Command("open", "-n", "-a", command, "--args", "--working-directory", projectDirectory);
                    default -> new Command("open", projectDirectory, "-a", command);
                };
            }

            default -> throw new RuntimeException("The environment is not supported: " + os);
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
