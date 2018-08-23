package main.java.Main;


import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {

    public static Path logsFolder = Paths.get(MyProperties.getInputFolder());
    public static LogScanner logScanner;

    public static void main(String[] args) {
        try {

            Interactive.geetingsImage();
            MainWindow.invoke();
            logScanner = new LogScanner(logsFolder);
            // TODO: 23.08.2018 delete
////            logScanner.startScanner();
//            XmlBuilder.createXmlReport();
        } finally {
            FileManager.moveFilesBack();
        }
    }
}

