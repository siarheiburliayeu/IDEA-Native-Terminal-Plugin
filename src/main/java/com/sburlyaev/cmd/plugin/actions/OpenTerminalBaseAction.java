package com.sburlyaev.cmd.plugin.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAwareAction;
import com.sburlyaev.cmd.plugin.CommandBuilder;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.settings.PluginSettings;
import com.sburlyaev.cmd.plugin.settings.PluginSettingsState;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public abstract class OpenTerminalBaseAction extends DumbAwareAction {

    private static final Logger log = Logger.getInstance(OpenTerminalBaseAction.class);

    @Deprecated
    protected static final String ENV_FAVORITE_TERMINAL = "FAVORITE_TERMINAL";

    protected static final Environment env = Environment.getEnvironment();

    @NotNull
    protected abstract String getDirectory(AnActionEvent event, PluginSettingsState settings);

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        log.info(env.toString());

        try {
            PluginSettingsState settings = PluginSettings.getInstance().getState();
            final String directory = getDirectory(event, settings);
            final String favoriteTerminalString = getFavoriteTerminal(settings);

            Command command = CommandBuilder.createCommand(env, directory, favoriteTerminalString);
            log.info(command.getCommands().toString());
            command.execute(directory);

        } catch (IOException e) {
            throw new RuntimeException("Failed to execute the command!", e);
        }
    }

    private String getFavoriteTerminal(PluginSettingsState settings) {
        // for compatibility with old versions (prior to v0.2)
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
