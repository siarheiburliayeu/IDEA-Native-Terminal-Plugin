package com.sburlyaev.cmd.plugin.settings;

import javax.swing.*;

public class PluginSettingsForm {

    private JTextField favoriteTerminalField;
    private JTextField subDirectoryField;
    private JPanel settingsPanel;
    private JButton btn_ft;
    private JButton btn_sd;

    public JPanel getSettingsPanel() {
        return settingsPanel;
    }

    public PluginSettingsState getSettingsState() {
        return new PluginSettingsState(favoriteTerminalField.getText(), subDirectoryField.getText());
    }

    public void setSettingsState(PluginSettingsState settingsState) {
        favoriteTerminalField.setText(settingsState.getFavoriteTerminal());
        subDirectoryField.setText(settingsState.getSubDirectory());
    }

    public JTextField getFavoriteTerminalField() {
        return favoriteTerminalField;
    }

    public JTextField getSubDirectoryField() {
        return subDirectoryField;
    }

    public JButton getBtn_ft() {
        return btn_ft;
    }

    public JButton getBtn_sd() {
        return btn_sd;
    }
}
