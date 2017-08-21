package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ToolWindowFactoryImpl implements ToolWindowFactory, ActionListener {
    private Project project;

    @Override
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
        this.project = project;

        JButton cmd = new JButton("Terminal 2");
        cmd.addActionListener(this);

        toolWindow.getContentManager().addContent(ContentFactory.SERVICE.getInstance().createContent(cmd, "", false));
    }

    @Override
    public void actionPerformed (ActionEvent event) {
        try {
            String path = project.getBaseDir().getCanonicalPath();
            System.out.println("project base dir: " + path);
            Runtime.getRuntime().exec("cmd /c \"start cmd /K \"cd /d " + path + "\"");  // cmd /c "start cmd /K "cd C:\Windows\""

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ToolWindow window) {
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return false;
    }

    @Override
    public boolean isDoNotActivateOnStart() {
        return false;
    }
}
