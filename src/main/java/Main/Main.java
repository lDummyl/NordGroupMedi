package main.java.Main;


import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static Path logsFolder = Paths.get(MyProperties.getInputFolder());
    public static LogScanner logScanner;

    public static void main(String[] args) {
        Interactive.geetingsImage();
        logScanner = new LogScanner(logsFolder);
        MainWindow.invoke();
    }
}

