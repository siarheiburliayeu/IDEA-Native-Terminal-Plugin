package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;

import java.io.IOException;

public class OpenCmd extends AnAction {

    private static final Logger log = Logger.getInstance(OpenCmd.class);

    protected static final String ENV_FAVORITE_TERMINAL = "FAVORITE_TERMINAL";

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            Environment env = Environment.getEnvironment();
            log.info(env.toString());

            final String projectBaseDir = getProjectBaseDir(event);
            final String favoriteTerminal = System.getenv(ENV_FAVORITE_TERMINAL);

            Command command = CommandBuilder.createCommand(env, projectBaseDir, favoriteTerminal);
            log.info(command.getCommands().toString());
            command.execute();

        } catch (IOException e) {
            throw new RuntimeException("Failed to execute the command!", e);
        }
    }

    private String getProjectBaseDir(AnActionEvent event) {
        Project project = event.getProject();
        return project != null
                ? project.getBaseDir().getCanonicalPath()
                : System.getProperty("user.home");
    }
}
