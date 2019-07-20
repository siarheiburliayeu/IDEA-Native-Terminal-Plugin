package com.sburlyaev.cmd.plugin.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.ex.FileChooserDialogImpl;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.sburlyaev.cmd.plugin.model.Terminal;
import org.apache.http.util.TextUtils;
import org.jdesktop.swingx.util.OS;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PluginSettingsConfigurable implements Configurable {

    private PluginSettingsForm pluginSettingsForm;
    private PluginSettings pluginSettings;

    private FileChooserDescriptor terminalChooserDescriptor;
    private final Project project;
    private VirtualFile selectedTerminal;

    private final String warningMessage = "The selected terminal currently is not supported and may not work properly";

    public PluginSettingsConfigurable() {
        // Set 'chooseFolders' depend on OS, because macOS application represents a directory.
        terminalChooserDescriptor = new FileChooserDescriptor(true, OS.isMacOSX(), false, false, false, false);

        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        if (openProjects.length > 0) {
            project = openProjects[0];
        } else {
            project = ProjectManager.getInstance().getDefaultProject();
        }
        pluginSettingsForm = new PluginSettingsForm();
        pluginSettings = PluginSettings.getInstance();

        // FileChooserDialog support  -longforus
        String favoriteTerminal = "";
        if (pluginSettings.getState() != null) {
            favoriteTerminal = pluginSettings.getState().getFavoriteTerminal();
        }
        if (!TextUtils.isEmpty(favoriteTerminal)) {
            selectedTerminal = VirtualFileManager.getInstance().findFileByUrl(getFileUrl(favoriteTerminal));
        }

        pluginSettingsForm.getTerminalFileChooserButton().addActionListener(e -> {
            VirtualFile[] chosenTerminals = new FileChooserDialogImpl(terminalChooserDescriptor, project)
                    .choose(project, selectedTerminal);

            if (chosenTerminals.length > 0) {
                VirtualFile file = chosenTerminals[0];
                if (file != null) {
                    String canonicalPath = file.getCanonicalPath();
                    Terminal terminal = Terminal.fromString(canonicalPath);
                    if (terminal == Terminal.GENERIC) {
                        Messages.showWarningDialog(warningMessage, "Warning");
                    }
                    selectedTerminal = file;
                    pluginSettingsForm.getFavoriteTerminalField().setText(canonicalPath);
                }
            }
        });
    }

    private String getFileUrl(String path) {
        return "file://" + path;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Native Terminal Plugin";
    }

    @Override
    public String getHelpTopic() {
        return "Configure Native Terminal Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return pluginSettingsForm.getSettingsPanel();
    }

    @Override
    public boolean isModified() {
        return !pluginSettingsForm.getSettingsState().equals(pluginSettings.getState());
    }

    @Override
    public void apply() {
        pluginSettings.loadState(pluginSettingsForm.getSettingsState());
    }

    @Override
    public void reset() {
        if (pluginSettings.getState() != null) {
            pluginSettingsForm.setSettingsState(pluginSettings.getState());
        }
    }

    @Override
    public void disposeUIResources() {
    }
}
