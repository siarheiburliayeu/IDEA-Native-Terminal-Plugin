package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.diagnostic.Logger;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;
import com.sburlyaev.cmd.plugin.model.Terminal;

public class CommandBuilder {

    private static final Logger log = Logger.getInstance(CommandBuilder.class);

    public static Command createCommand(Environment env, String projectBaseDir, String favoriteTerminal) {
        OperationSystem os = env.getOs();
        StringBuilder builder = new StringBuilder();

        String command = favoriteTerminal == null
                ? os.getDefaultTerminal().getCommand()
                : favoriteTerminal;
        Terminal terminal = Terminal.fromString(command);
        log.info("Favorite terminal is [" + favoriteTerminal + "] and using [" + terminal + "]");

        switch (os) {
            case WINDOWS:

                switch (terminal) {
                    case COMMAND_PROMPT:
                        return new Command("cmd", "/c", "start", command, "/K", "cd", "/d", projectBaseDir);

                    case POWER_SHELL:
                        builder.append("cmd /c start ")
                                .append(command)
                                .append(" -NoExit")
                                .append(" -Command")
                                .append(" \"Set-Location '")
                                .append(projectBaseDir)
                                .append("'\"");
                        break;
                    case CON_EMU:
                        String conEmuRunCommand = " -run ";
                        String[] commands = command.split(conEmuRunCommand);

                        builder.append(commands[0])
                                .append(" -Dir ")
                                .append("\"")
                                .append(projectBaseDir)
                                .append("\"");
                        if (commands.length == 2) {
                            builder.append(conEmuRunCommand)
                                    .append(commands[1]);
                        }
                        break;
                    case GIT_BASH:
                        builder.append(command)
                                .append(" --cd=")
                                .append("\"")
                                .append(projectBaseDir)
                                .append("\"");
                        break;
                    default:
                        builder.append(command);
                        break;
                }
                break;

            case LINUX:

                switch (terminal) {
                    case GNOME_TERMINAL:
                        return new Command(command, "--working-directory", projectBaseDir);
                    default:
                        builder.append(command);
                        break;
                }
                break;

            case MAC_OS:  // Terminal, iTerm
                return new Command("open", projectBaseDir, "-a", command);

            default:
                throw new RuntimeException("The environment is not supported: " + os);
        }
        return new Command(builder.toString());
    }
}
