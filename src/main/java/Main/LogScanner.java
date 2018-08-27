package main.java.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;


public class LogScanner {

    private final Path logsFolder;
    private boolean active;
    private long timePeriod;


    public LogScanner(Path logsFolder) {
        this.logsFolder = logsFolder;
    }


    public void callScanner()  {
        active = true;
        new Thread(()-> startScanner()).start();
    }

    public void startScanner()  {
        while (active) {
            System.out.println("scanning: " + logsFolder.toFile().getAbsolutePath());
            scanFolder();
        }
    }
    private void scanFolder(){
        try {
            Files.walk(logsFolder)
                    .filter(p -> p.toString().endsWith(".xml"))
                    .distinct()
                    .forEach(XmlProcessor::sendToQueue);
            Thread.sleep(timePeriod);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void shutDown(){
        active = false;
        try {
            XmlProcessor.threadService.shutdown();
            XmlProcessor.threadService.awaitTermination(5L, TimeUnit.SECONDS);
            if (!XmlProcessor.daysMap.isEmpty()) {
                XmlBuilder.createXmlReport();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setTimePeriod(long timePeriod) {
        this.timePeriod = timePeriod;
    }
}


