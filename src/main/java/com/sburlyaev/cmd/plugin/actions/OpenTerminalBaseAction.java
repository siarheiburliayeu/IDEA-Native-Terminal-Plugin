package com.sburlyaev.cmd.plugin.actions;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.sburlyaev.cmd.plugin.CommandBuilder;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.settings.PluginSettings;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;

public abstract class OpenTerminalBaseAction extends AnAction {

    private static final Logger log = Logger.getInstance(OpenTerminalBaseAction.class);

    @Deprecated
    protected static final String ENV_FAVORITE_TERMINAL = "FAVORITE_TERMINAL";

    @NotNull
    protected abstract String getDirectory(AnActionEvent event, PluginSettingsState settings);

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            // todo: move to static field?
            Environment env = Environment.getEnvironment();
            log.info(env.toString());

            PluginSettingsState settings = PluginSettings.getInstance().getState();
            final String directory = getDirectory(event, settings);
            final String favoriteTerminalString = getFavoriteTerminal(settings);

            Command command = CommandBuilder.createCommand(env, directory, favoriteTerminalString);
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
}
