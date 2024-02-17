package com.sburlyaev.cmd.plugin;

import com.sburlyaev.cmd.plugin.model.Command;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JustForTroubleshooting {

    private static final Pattern OS_VERSION_PATTERN = Pattern.compile("^(\\d+\\.\\d+).*");

    public static void main(String[] args) {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String gui = System.getProperty("sun.desktop");

        String shortName = osName.substring(0, 3).toLowerCase();
        System.out.println("OS name: " + osName + " (" + shortName + ")");
        System.out.println("OS version: " + osVersion);
        System.out.println("Parsed version: " + parseOsVersion(osVersion)); // windows only?
        System.out.println("GUI: " + gui);

        System.out.println("/usr/bin/gnome-terminal: " + new File("/usr/bin/gnome-terminal").exists());
        System.out.println("/usr/bin/konsole: " + new File("/usr/bin/konsole").exists());

        String dir = System.getProperty("user.dir");

        Command command = new Command("wt", "-d", dir);
        System.out.println(command);
        System.out.println(dir);
        //command.execute();
    }

    private static double parseOsVersion(String windowsVersion) {
        double version = -1.0;
        try {
            Matcher matcher = OS_VERSION_PATTERN.matcher(windowsVersion);
            if (matcher.matches()) {
                String group1 = matcher.group(1);
                version = Double.parseDouble(group1);
            }
        } catch (Exception e) {
            System.out.println("Failed to parse Windows version: " + windowsVersion);
        }
        return version;
    }

}
