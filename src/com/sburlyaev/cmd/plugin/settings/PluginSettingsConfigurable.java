package com.sburlyaev.cmd.plugin.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PluginSettingsConfigurable implements Configurable {

    private PluginSettingsForm pluginSettingsForm;
    private PluginSettings pluginSettings;

    public PluginSettingsConfigurable() {
        this.pluginSettingsForm = new PluginSettingsForm();
        this.pluginSettings = com.sburlyaev.cmd.plugin.settings.PluginSettings.getInstance();
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
