package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

import java.io.IOException;

public class OpenCmd extends AnAction {
    private static final Logger LOG = Logger.getInstance(OpenCmd.class);

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            final String osName = System.getProperty("os.name");
            final String osVersion = System.getProperty("os.version");
            final String gui = System.getProperty("sun.desktop");

            LOG.info("OS: " + osName + " " + osVersion + " with " + gui);

            Project project = event.getProject();
            final String projectBaseDir = project != null
                    ? project.getBaseDir().getCanonicalPath()
                    : System.getProperty("user.home");

            openCmd(osName, osVersion, gui, projectBaseDir);

        } catch (Exception e) {
            LOG.warn(e);
        }
    }

    private void openCmd(String osName, String osVersion, String gui, String projectBaseDir) throws IOException {
        String command;

        switch (osName.toLowerCase().substring(0,3)) {
            case "win":
                command = "cmd /c \"start cmd /K \"cd /d " + projectBaseDir + "\"";
                break;

            case "lin":
                if ("gnome".equals(gui)) {
                    command = "gnome-terminal --working-directory=" + projectBaseDir;
                } else {
                    LOG.warn("The GUI is not supported: " + gui + "(" + osName + " " + osVersion + ")");
                    return;
                }
                break;

            case "mac":
                command = "";
                break;

            default:
                LOG.warn("The OS is not supported: " + osName + " " + osVersion);
                return;
        }
        LOG.info("Command: " + command);
        Runtime.getRuntime().exec(command);
    }
}
