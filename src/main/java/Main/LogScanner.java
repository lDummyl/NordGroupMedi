package main.java.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class LogScanner {

    private final Path logsFolder;
    private boolean active;
    private long timePeriod;


    public LogScanner(Path logsFolder) {
        this.logsFolder = logsFolder;
    }


    public void startScanner()  {
        active = true;
        new Thread(()-> startScanner1()).start();
    }

    public void startScanner1()  {
        while (active) {
            System.out.println("scanning");
            scanFolder();
        }
    }
    private void scanFolder(){
        try {
            Files.walk(logsFolder)
                    .filter(p -> p.toString().endsWith(".xml"))
                    .distinct()
                    .forEach(XmlProcesser::sendToQueue);
            Thread.sleep(timePeriod);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void shutDown(){
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setTimePeriod(long timePeriod) {
        this.timePeriod = timePeriod;
    }
}


