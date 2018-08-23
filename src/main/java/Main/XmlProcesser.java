package main.java.Main;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class XmlProcesser {

    static Path archive = Paths.get(MyProperties.getLogsArchiveFolder());
    static Queue<Path> queue = new LinkedList<>();
    static ExecutorService threadService = Executors.newFixedThreadPool(10);
    static TreeMap<LocalDate, Map<User, Map<URL,Average>>> daysMap = new TreeMap<>();

    private static boolean processing;
    static Thread processThread;


    private static void invokeProcessThread(){
        if(processThread == null || !processThread.isAlive()){
            processThread = new Thread(() -> startQueueProcessing());
            processThread.start();
        }
    }

    public static void sendToQueue(Path path){

        invokeProcessThread();

        Path tempFolder = Paths.get(MyProperties.getTempFolder());
        Path pathInTemp = FileManager.sendFileTo(path, tempFolder);
        queue.add(pathInTemp);
    }

    private static void callThread(Path path){
        threadService.submit(new XmlProcess(path));
    }

     static synchronized void  insertDataToMainMap(Map.Entry<LocalDate, Integer> entry, User user, URL url) {

        Integer seconds = entry.getValue();
        Average average = new Average(seconds,1);
        HashMap<URL, Average> singleAverage = new HashMap<>();
        singleAverage.put(url,average);
        TreeMap<User, Map<URL, Average>> userSites = new TreeMap<>();
        userSites.put(user,singleAverage);
        Map<User, Map<URL, Average>> map = daysMap.get(entry.getKey());
        if (map == null || map.isEmpty()){
            daysMap.put(entry.getKey(),userSites);
        }else{
            Map<URL, Average> urlAverageMap = map.get(user);
            if(urlAverageMap == null || urlAverageMap.isEmpty()){
                map.put(user,singleAverage);
            }else{
                Average averageInMap = urlAverageMap.get(url);
                if (averageInMap == null){
                    urlAverageMap.put(url,average);
                }else{
                    averageInMap.addOneVisit(seconds);
                }
            }
        }
    }

    private static void startQueueProcessing() {
        processing = true;
        while(processing){
            Path path = getNextByQueue();
            if (path !=null){
                System.out.println("processing");
                callThread(path);
            }else{
                // without it, resources are not consumed in large quantities but just in case.
                try {
                    Thread.sleep(1000);
                    if(!Main.logScanner.isActive()){
                        stopQueueProcessing();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void stopQueueProcessing() {
        processing = false;
    }

    private static synchronized Path getNextByQueue(){
        return queue.poll();
    }
}
