package com.sburlyaev.cmd.plugin.actions;

import java.io.File;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;

public class OpenProjectDirectoryAction extends OpenTerminalBaseAction {

    @NotNull
    @Override
    protected String getDirectory(AnActionEvent event, PluginSettingsState settings) {
        Project project = event.getProject();
        if (project == null) {
            return System.getProperty("user.home");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(project.getBasePath());
        if (settings != null) {
            // Introduce subdirectory support (v0.2)
            String subDirectory = settings.getSubDirectory();
            if (subDirectory != null && !subDirectory.isEmpty()) {
                File file = new File(subDirectory);
                if (file.exists()) {
                    return subDirectory;  // Another directory support -longforus
                }
                if (!(subDirectory.startsWith("/") || subDirectory.startsWith("\\"))) {
                    sb.append(File.separatorChar);
                }
                sb.append(subDirectory);
            }
        }
        return sb.toString();
    }
}
