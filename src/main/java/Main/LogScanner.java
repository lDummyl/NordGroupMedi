package main.java.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class LogScanner {

    private final Path logsFolder;
    private boolean active = true;
    private long timePeriod;  //in millis
//            1000 * MyProperties.getScanFriquencyInSec();

    // TODO: 23.08.2018 delete
//    public static int counter = 0;//temp

    public LogScanner(Path logsFolder) {
        this.logsFolder = logsFolder;
    }


    public void startScanner()  {
        active = true;
        new Thread(()-> startScanner1()).start();
    }

    public void startScanner1()  {
        while (active) {
            System.out.println("scannin");
            scanFolder();
            // TODO: 23.08.2018 delete
//            counter++;//temp
//            if (counter>7) shutDown();//temp
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


