package com.sburlyaev.cmd.plugin.settings;

import javax.swing.*;

public class PluginSettingsForm {

    private JPanel settingsPanel;
    private JTextField favoriteTerminalField;
    private JButton terminalFileChooserButton;

    public JPanel getSettingsPanel() {
        return settingsPanel;
    }

    public PluginSettingsState getSettingsState() {
        return new PluginSettingsState(favoriteTerminalField.getText());
    }

    public void setSettingsState(PluginSettingsState settingsState) {
        favoriteTerminalField.setText(settingsState.getFavoriteTerminal());
    }

    public JTextField getFavoriteTerminalField() {
        return favoriteTerminalField;
    }

    public JButton getTerminalFileChooserButton() {
        return terminalFileChooserButton;
    }
}
