package com.sburlyaev.cmd.plugin.actions;

import com.intellij.ide.actions.RevealFileAction;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetAsDefaultDirectoryAction extends DumbAwareAction {

    private static final Logger log = Logger.getInstance(SetAsDefaultDirectoryAction.class);

    public static final String DEFAULT_DIRECTORY_PROPERTY_KEY = "com.sburlyaev.terminal.plugin.properties.directory";

    @Override
    public void update(@NotNull AnActionEvent event) {
        Project project = getEventProject(event);
        event.getPresentation().setEnabledAndVisible(project != null && getSelectedFile(event) != null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = getEventProject(e);
        VirtualFile directory = getSelectedDirectory(e);
        if (project == null || directory == null)
            return;

        PropertiesComponent properties = PropertiesComponent.getInstance(project);
        properties.setValue(DEFAULT_DIRECTORY_PROPERTY_KEY, directory.getPath());

        log.info("'" + properties.getValue(DEFAULT_DIRECTORY_PROPERTY_KEY) +
                "' is set as default directory for project: " + project.getName());
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

}
