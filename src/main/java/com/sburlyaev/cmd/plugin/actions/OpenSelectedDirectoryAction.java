package com.sburlyaev.cmd.plugin.actions;

import com.intellij.ide.actions.ShowFilePathAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OpenSelectedDirectoryAction extends OpenTerminalBaseAction {

    @NotNull
    @Override
    protected String getDirectory(AnActionEvent event, PluginSettingsState settings) {
        VirtualFile directory = getDirectory(event);
        if (directory == null) {
            return System.getProperty("user.home");
        }
        return directory.getPath();
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        Project project = getEventProject(event);
        event.getPresentation().setEnabledAndVisible(project != null && getSelectedFile(event) != null);
    }

    @Nullable
    private static VirtualFile getSelectedFile(@NotNull AnActionEvent event) {
        return ShowFilePathAction.findLocalFile(event.getData(CommonDataKeys.VIRTUAL_FILE));
    }

    @Nullable
    private VirtualFile getDirectory(@NotNull AnActionEvent event) {
        VirtualFile file = getSelectedFile(event);
        if (file == null) return null;

        return file.isDirectory() ? file : file.getParent();
    }
}
