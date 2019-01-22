package com.sburlyaev.cmd.plugin.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "com.sburlyaev.cmd.plugin.PluginSettings",
        storages = @Storage("nativeTerminalPlugin.xml"))
public class PluginSettings implements PersistentStateComponent<PluginSettingsState> {

    private PluginSettingsState PluginSettingsState;

    public static PluginSettings getInstance() {
        return ServiceManager.getService(PluginSettings.class);
    }

    @Nullable
    @Override
    public PluginSettingsState getState() {
        return PluginSettingsState;
    }

    @Override
    public void loadState(@NotNull PluginSettingsState state) {
        PluginSettingsState = state;
    }
}