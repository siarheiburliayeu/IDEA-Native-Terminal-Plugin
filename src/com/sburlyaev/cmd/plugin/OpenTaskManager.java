package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.io.IOException;

public class OpenTaskManager extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            Runtime.getRuntime().exec("cmd /c taskmgr");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
