package com.sburlyaev.cmd.plugin.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.vfs.VirtualFile;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;
import org.jetbrains.annotations.NotNull;

public class OpenSelectedDirectoryAction extends OpenTerminalBaseAction {

    private static final String JAR_LIBRARY_EXTENSION = ".jar!";

    @NotNull
    @Override
    protected String getDirectory(AnActionEvent event, PluginSettingsState settings) {
        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);
        return getDir(file).getPath();
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);
        Presentation presentation = event.getPresentation();
        presentation.setVisible(file != null && getDir(file) != null);
    }

    protected VirtualFile getDir(@NotNull VirtualFile file) {
        if (file.getPath().contains(JAR_LIBRARY_EXTENSION)) {
            return null;
        }
        return file.isDirectory() ? file : file.getParent();
    }
}
