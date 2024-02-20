package com.sburlyaev.cmd.plugin.actions;

import com.intellij.ide.actions.RevealFileAction;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OpenSelectedDirectoryAction extends OpenTerminalBaseAction {

    @Override
    public void update(@NotNull AnActionEvent event) {
        Project project = getEventProject(event);
        event.getPresentation().setEnabledAndVisible(
                project != null && getSelectedFile(event) != null);
    }

    @NotNull
    @Override
    protected String getDirectory(AnActionEvent event, PluginSettingsState settings) {
        VirtualFile directory = getSelectedDirectory(event);
        if (directory == null) {
            return System.getProperty("user.home");
        }
        return directory.getPath();
    }

    @Nullable
    private static VirtualFile getSelectedFile(@NotNull AnActionEvent event) {
        return RevealFileAction.findLocalFile(event.getData(CommonDataKeys.VIRTUAL_FILE));
    }

    @Nullable
    private VirtualFile getSelectedDirectory(@NotNull AnActionEvent event) {
        VirtualFile file = getSelectedFile(event);
        return file != null
                ? file.isDirectory() ? file : file.getParent()
                : null;
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }
}
