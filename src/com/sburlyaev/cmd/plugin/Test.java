package com.sburlyaev.cmd.plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) throws IOException {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String gui = System.getProperty("sun.desktop");

        String shortName = osName.substring(0, 3).toLowerCase();
        System.out.println("OS name: " + osName + " (" + shortName + ")");
        System.out.println("OS version : " + osVersion);
        System.out.println("GUI: " + gui);

        String favoriteTerminal = System.getenv(OpenCmd.ENV_FAVORITE_TERMINAL);
        System.out.println(OpenCmd.ENV_FAVORITE_TERMINAL + ": " + favoriteTerminal);

        String command1 = "open";
        String command2 = "/Users/user/IdeaProjects/Project With Spaces";
        String command3 = "-a";
        String command4 = "Terminal";

        ProcessBuilder processBuilder = new ProcessBuilder(command1, command2, command3, command4);
        System.out.println(processBuilder.command());
        Process process = processBuilder.start();

        System.out.println(new BufferedReader(new InputStreamReader(process.getErrorStream()))
                .lines().collect(Collectors.joining("\n")));
    }
}
