package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.service.execution.NotSupportedException;
import com.intellij.openapi.project.Project;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;

import java.io.IOException;

public class OpenCmd extends AnAction {

    private static final Logger LOG = Logger.getInstance(OpenCmd.class);

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            Environment env = getEnvironment();
            LOG.info(env.toString());

            Project project = event.getProject();
            final String projectBaseDir = project != null
                    ? project.getBaseDir().getCanonicalPath()
                    : System.getProperty("user.home");

            Command command = createCommand(env, projectBaseDir);
            LOG.info("Command: " + command.getCommand());
            command.execute();

        } catch (IOException e) {
            throw new RuntimeException("Failed to execute the command!", e);
        }
    }

    private Environment getEnvironment() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String gui = System.getProperty("sun.desktop");

        OperationSystem os = OperationSystem.fromString(osName);

        return new Environment(os, osVersion, gui);
    }

    protected Command createCommand(Environment env, String projectBaseDir) {
        OperationSystem os = env.getOs();
        String command;

        switch (os) {
            case WINDOWS:
                command = "cmd /c \"start cmd /K \"cd /d " + projectBaseDir + "\"";
                break;

            case LINUX:
                command = "gnome-terminal --working-directory=" + projectBaseDir;
                break;

            case MAC_OS:
                command = "open -a Terminal.app --new --fresh --args cd " + projectBaseDir;
                break;

            default:
                throw new NotSupportedException("The environment is not supported: " + os);
        }
        return new Command(command);
    }
}
