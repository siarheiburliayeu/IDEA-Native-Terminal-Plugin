package com.sburlyaev.cmd.plugin.actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;
import org.jetbrains.annotations.NotNull;

import static com.sburlyaev.cmd.plugin.actions.SetAsDefaultDirectoryAction.DEFAULT_DIRECTORY_PROPERTY_KEY;

public class OpenProjectDirectoryAction extends OpenTerminalBaseAction {

    @NotNull
    @Override
    protected String getDirectory(AnActionEvent event, PluginSettingsState settings) {  // todo: settings are not required anymore
        Project project = getEventProject(event);
        if (project == null) {
            return System.getProperty("user.home");
        }

        PropertiesComponent properties = PropertiesComponent.getInstance(project);
        String defaultDirectory = properties.getValue(DEFAULT_DIRECTORY_PROPERTY_KEY);

        if (defaultDirectory == null) {
            defaultDirectory = project.getBasePath();
            if (defaultDirectory == null) {
                defaultDirectory = System.getProperty("user.home");
            }
        }
        return defaultDirectory;
    }
}
