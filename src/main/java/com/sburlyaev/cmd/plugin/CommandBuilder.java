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
        checkProjectDirectory(projectDirectory);

        String command = favoriteTerminalString != null
                ? favoriteTerminalString
                : env.getDefaultTerminal().getCommand();

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
                        double windowsVersion = OperationSystem.parseOsVersion(env.getOsVersion());

                        if (windowsVersion < 10.0) {
                            // todo: prior to Windows 10 (Windows 8.1, 8, 7, Vista, XP, 2000)
                            return new Command(command, "/task", "cmder", projectDirectory);
                        } else {
                            // Windows 10
                            return new Command(command, "/start", projectDirectory);
                        }

                    case GIT_BASH:
                        return new Command(command, "--cd=" + projectDirectory);

                    case BASH:
                        return new Command("cmd", "/k", "start", "/d",
                                projectDirectory.replace("/", "\\"), command);

                    default:
                        return new Command("cmd", "/c", "start", command);
                }

            case LINUX:
                // todo: params support
                String command1 = command;
                String param = null;
                if (command.contains(" ")) {
                    String[] split = command.split(" ");
                    command1 = split[0];
                    param = split[1];
                }

                switch (terminal) {
                    case GNOME_TERMINAL:
                        return new Command(command1, param, "--working-directory", projectDirectory);
                    case KONSOLE:
                        return new Command(command1, param, "--workdir", projectDirectory);
                    case RXVT:
                        return new Command(command1, param, "-cd", projectDirectory);

                    default:
                        if (param != null) {
                            return new Command(command1, param, projectDirectory);
                        }
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
