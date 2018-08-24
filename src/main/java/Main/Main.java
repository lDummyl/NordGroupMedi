package main.java.Main;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {

    public static Path logsFolder = Paths.get(MyProperties.getInputFolder());
    public static LogScanner logScanner;


    public static void main(String[] args) {

        Interactive.geetingsImage();
        logScanner = new LogScanner(logsFolder);
        MainWindow.invoke();

    }
}

