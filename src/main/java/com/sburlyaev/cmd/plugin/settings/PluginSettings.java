package com.sburlyaev.cmd.plugin.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "com.sburlyaev.cmd.plugin.PluginSettings",
    storages = @Storage("nativeTerminalPlugin.xml"))
public class PluginSettings implements PersistentStateComponent<PluginSettingsState> {

    private PluginSettingsState pluginSettingsState;

    public static PluginSettings getInstance() {
        return ApplicationManager.getApplication().getService(PluginSettings.class);
    }

    @Nullable
    @Override
    public PluginSettingsState getState() {
        return pluginSettingsState;
    }

    @Override
    public void loadState(@NotNull PluginSettingsState state) {
        pluginSettingsState = state;
    }

}
