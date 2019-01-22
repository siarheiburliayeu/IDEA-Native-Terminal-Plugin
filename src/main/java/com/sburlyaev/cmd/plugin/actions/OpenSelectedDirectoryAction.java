package com.sburlyaev.cmd.plugin.actions;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.vfs.VirtualFile;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;

public class OpenSelectedDirectoryAction extends OpenTerminalBaseAction {

    @NotNull
    @Override
    protected String getDirectory(AnActionEvent event, PluginSettingsState settings) {
        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);
        return file.isDirectory() ? file.getPath() : file.getParent().getPath();
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        VirtualFile file = event.getData(CommonDataKeys.VIRTUAL_FILE);
        Presentation presentation = event.getPresentation();
        presentation.setVisible(file != null);
    }
}
