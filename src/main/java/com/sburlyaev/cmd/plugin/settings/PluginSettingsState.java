package com.sburlyaev.cmd.plugin.settings;

import java.util.Objects;

public class PluginSettingsState {

    private String favoriteTerminal;
    private String subDirectory;

    public PluginSettingsState() {
    }

    public PluginSettingsState(String favoriteTerminal, String subDirectory) {
        this.favoriteTerminal = favoriteTerminal;
        this.subDirectory = subDirectory;
    }

    public String getFavoriteTerminal() {
        return favoriteTerminal;
    }

    public void setFavoriteTerminal(String favoriteTerminal) {
        this.favoriteTerminal = favoriteTerminal;
    }

    public String getSubDirectory() {
        return subDirectory;
    }

    public void setSubDirectory(String subDirectory) {
        this.subDirectory = subDirectory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginSettingsState that = (PluginSettingsState) o;
        return Objects.equals(favoriteTerminal, that.favoriteTerminal) &&
                Objects.equals(subDirectory, that.subDirectory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteTerminal, subDirectory);
    }
}
