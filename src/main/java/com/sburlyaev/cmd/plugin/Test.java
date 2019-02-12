package com.sburlyaev.cmd.plugin;

import java.io.IOException;

import com.sburlyaev.cmd.plugin.actions.OpenTerminalBaseAction;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.OperationSystem;

public class Test {

    public static void main(String[] args) throws IOException {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String gui = System.getProperty("sun.desktop");

        String shortName = osName.substring(0, 3).toLowerCase();
        System.out.println("OS name: " + osName + " (" + shortName + ")");
        System.out.println("OS version: " + osVersion);
        System.out.println("Parsed version: " + Double.parseDouble(osVersion));
        System.out.println("GUI: " + gui);

//        String favoriteTerminal = System.getenv(OpenTerminalBaseAction.ENV_FAVORITE_TERMINAL);
//        System.out.println(OpenTerminalBaseAction.ENV_FAVORITE_TERMINAL + ": " + favoriteTerminal);

        String command1 = "open";
        String command2 = "/Users/user/IdeaProjects/Project With Spaces";
        String command3 = "-a";
        String command4 = "Terminal";

        // bash on windows
//        Command commandX = new Command("cmd", "/k", "start",
//                "/d", "C:/Users/Siarhei_Burliayeu".replace("/", "\\"), "bash");
//        System.out.println(commandX.getCommands());

        Command commandCC = new Command("C:/cmder_mini/Cmder.exe", "/start", "C:/Users/Siarhei_Burliayeu/IdeaProjects");
        Command commandCD = new Command("C:/cmder_mini/Cmder.exe", "/start", "D:/Users/Siarhei_Burliayeu/IdeaProjects");
        Command commandDC = new Command("D:/cmder_mini/Cmder.exe", "/start", "C:/Users/Siarhei_Burliayeu/IdeaProjects");
        Command commandDD = new Command("D:/cmder_mini/Cmder.exe", "/start", "D:/Users/Siarhei_Burliayeu/IdeaProjects");
        Command commandCC2 = new Command("C:/cmder_mini/Cmder.exe", "/task", "cmder", "C:/Users/Siarhei_Burliayeu/IdeaProjects");
        Command commandCD2 = new Command("C:/cmder_mini/Cmder.exe", "/task", "cmder", "D:/Users/Siarhei_Burliayeu/IdeaProjects");
        Command commandDC2 = new Command("D:/cmder_mini/Cmder.exe", "/task", "cmder", "C:/Users/Siarhei_Burliayeu/IdeaProjects");
        Command commandDD2 = new Command("D:/cmder_mini/Cmder.exe", "/task", "cmder", "D:/Users/Siarhei_Burliayeu/IdeaProjects");
        commandCD2.execute();
    }
}
