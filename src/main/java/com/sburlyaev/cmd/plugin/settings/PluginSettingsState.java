package com.sburlyaev.cmd.plugin.settings;

import java.util.Objects;

public class PluginSettingsState {

    private String favoriteTerminal;

    public PluginSettingsState() {
    }

    public PluginSettingsState(String favoriteTerminal) {
        this.favoriteTerminal = favoriteTerminal;
    }

    public String getFavoriteTerminal() {
        return favoriteTerminal;
    }

    public void setFavoriteTerminal(String favoriteTerminal) {
        this.favoriteTerminal = favoriteTerminal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginSettingsState that = (PluginSettingsState) o;
        return Objects.equals(favoriteTerminal, that.favoriteTerminal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteTerminal);
    }
}
