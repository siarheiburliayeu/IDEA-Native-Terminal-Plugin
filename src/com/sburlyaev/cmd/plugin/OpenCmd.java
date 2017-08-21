package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

public class OpenCmd extends AnAction {
    private static final Logger LOG = Logger.getInstance(OpenCmd.class);

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            final String osName = System.getProperty("os.name");
            LOG.info("OS: " + osName);

            Project project = event.getProject();
            final String projectBaseDir = project != null
                    ? project.getBaseDir().getCanonicalPath()
                    : System.getProperty("user.home");

            final String command = "cmd /c \"start cmd /K \"cd /d " + projectBaseDir + "\"";
            LOG.info("Command: " + command);
            Runtime.getRuntime().exec(command);

        } catch (Exception e) {
            LOG.warn(e);
        }
    }
}
