package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import org.jetbrains.annotations.NotNull;

public class ErrorReporter extends ErrorReportSubmitter {

    @NotNull
    @Override
    public String getReportActionText() {
        return null;
    }
}
