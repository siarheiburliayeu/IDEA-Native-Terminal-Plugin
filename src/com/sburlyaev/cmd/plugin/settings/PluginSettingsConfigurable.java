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
import java.io.File;
import javax.swing.JComponent;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

public class PluginSettingsConfigurable implements Configurable {

    private PluginSettingsForm pluginSettingsForm;
    private PluginSettings pluginSettings;
    private FileChooserDescriptor fcdFt = new FileChooserDescriptor(true, false, false, false, false, false);
    private FileChooserDescriptor fcdSd = new FileChooserDescriptor(false, true, false, false, false, false);
    private final Project mProject;
    private VirtualFile mSelectedTerminal = null;
    private VirtualFile mSelectedSubDir = null;

    public PluginSettingsConfigurable() {
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        if (openProjects.length > 0) {
            mProject = openProjects[0];
        } else {
            mProject = ProjectManager.getInstance().getDefaultProject();
        }
        this.pluginSettingsForm = new PluginSettingsForm();
        this.pluginSettings = com.sburlyaev.cmd.plugin.settings.PluginSettings.getInstance();
        //Add FileChooserDialog support  -longforus
        String favoriteTerminal = "";
        if (pluginSettings.getState() != null) {
            favoriteTerminal =  pluginSettings.getState().getFavoriteTerminal();
        }
        if (!TextUtils.isEmpty(favoriteTerminal)) {
            mSelectedTerminal = VirtualFileManager.getInstance().findFileByUrl(getFileUrl(favoriteTerminal));
        }
        String subDirectory = "";
        if (pluginSettings.getState() != null) {
            subDirectory =  pluginSettings.getState().getSubDirectory();
        }
        if (!TextUtils.isEmpty(subDirectory)) {
            File file = new File(subDirectory);
            if (file.exists()) {
                mSelectedSubDir = VirtualFileManager.getInstance().findFileByUrl(getFileUrl(subDirectory));
            } else {
                file = new File(mProject.getBasePath() + subDirectory);
                if (file.exists()) {
                    mSelectedSubDir = VirtualFileManager.getInstance().findFileByUrl(getFileUrl(file.getPath()));
                }
            }
        }
        if (mSelectedSubDir == null) {
            mSelectedSubDir = mProject.getBaseDir();
        }
        pluginSettingsForm.getBtn_ft().addActionListener(e -> {
            VirtualFile[] chooseTerminal = new FileChooserDialogImpl(fcdFt, mProject).choose(mProject, mSelectedTerminal);
            VirtualFile file = chooseTerminal[0];
            if (file != null) {
                String canonicalPath = file.getCanonicalPath();
                Terminal terminal = Terminal.fromString(canonicalPath);
                if (terminal != Terminal.GENERIC) {
                    mSelectedTerminal = file;
                    pluginSettingsForm.getFavoriteTerminalField().setText(canonicalPath);
                } else {
                    Messages.showErrorDialog("This Terminal is not supported", "Error");
                }
            }
        });
        pluginSettingsForm.getBtn_sd().addActionListener(e -> {
            VirtualFile[] chooseTerminal = new FileChooserDialogImpl(fcdSd, mProject).choose(mProject, mSelectedSubDir);
            mSelectedSubDir = chooseTerminal[0];
            if (mSelectedSubDir != null) {
                String subDirCanonicalPath = mSelectedSubDir.getCanonicalPath();
                if (subDirCanonicalPath != null&&mProject.getBasePath()!=null&&subDirCanonicalPath.startsWith(mProject.getBasePath())) {
                    subDirCanonicalPath = subDirCanonicalPath.replace(mProject.getBasePath(), "");
                }
                pluginSettingsForm.getSubDirectoryField().setText(subDirCanonicalPath);
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
