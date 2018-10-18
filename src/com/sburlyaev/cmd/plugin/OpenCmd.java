package com.sburlyaev.cmd.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.settings.PluginSettings;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;

public class OpenCmd extends AnAction {

    private static final Logger log = Logger.getInstance(OpenCmd.class);

    @Deprecated
    protected static final String ENV_FAVORITE_TERMINAL = "FAVORITE_TERMINAL";

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            Environment env = Environment.getEnvironment();
            log.info(env.toString());

            PluginSettingsState settings = PluginSettings.getInstance().getState();
            final String projectDirectory = getProjectDirectory(event, settings);
            final String favoriteTerminalString = getFavoriteTerminal(settings);

            Command command = CommandBuilder.createCommand(env, projectDirectory, favoriteTerminalString);
            log.info(command.getCommands().toString());
            command.execute();

        } catch (IOException e) {
            throw new RuntimeException("Failed to execute the command!", e);
        }
    }

    private String getFavoriteTerminal(PluginSettingsState settings) {
        // to be compatible with old versions (prior to v0.2)
        final String envFavoriteTerminal = System.getenv(ENV_FAVORITE_TERMINAL);

        if (settings != null) {
            String favoriteTerminalString = settings.getFavoriteTerminal();
            if (favoriteTerminalString != null && !favoriteTerminalString.isEmpty()) {
                return favoriteTerminalString;
            }
        }
        return envFavoriteTerminal;
    }

    private String getProjectDirectory(AnActionEvent event, PluginSettingsState settings) {
        Project project = event.getProject();
        if (project == null) {
            return System.getProperty("user.home");
        }

        if (settings != null && settings.isOpenCurrent()) {
            FileEditorManager manager = FileEditorManager.getInstance(project);
            VirtualFile[] openFiles = manager.getSelectedFiles();
            if (openFiles.length > 0) {
                VirtualFile openFile = openFiles[0];
                return Paths.get(openFile.getPath()).getParent().toString();
            }
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
