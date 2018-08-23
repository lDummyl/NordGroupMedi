package main.java.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager {


    private static boolean archive;
    private static ArrayList<Path> toArchive = new ArrayList<>();

    public static Path sendFileTo(Path from, Path to){
        try {
            Path fileName = from.getFileName();
            Path directionPath =
                    Paths.get(to.toString() + "\\"+ fileName.toString());
            Files.move(from, directionPath);
            return directionPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: 23.08.2018 delete from here...
    public static void sendToSourceFolder(Path path) {
        sendFileTo(path, Main.logsFolder);
    }

    public static void moveFilesBack(){
        try {
            Files.walk(XmlProcesser.archive)
                    .filter(p -> p.toString().endsWith(".xml"))
                    .distinct()
                    .forEach(FileManager::sendToSourceFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // TODO: 23.08.2018 ..to here.

    public static void archiveFile(Path path) {
        toArchive.add(path);

    }


    public static void processTempFiles() {
        if (archive){
            archiveTempFiles();
        }else {
            deleteTempFiles();
        }
    }

    private static void deleteTempFiles() {
        for (Path path : toArchive) {
            path.toFile().delete();
        }
    }

    private static void archiveTempFiles() {

        if(!toArchive.isEmpty()) {

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                String archiveName = String.format(MyProperties.getLogsArchiveFolder().toString() +
                        "//logs_at_%s.zip", LocalDateTime.now().format(formatter));

                FileOutputStream fos = new FileOutputStream(archiveName);
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                for (Path path : toArchive) {
                    File fileToZip = path.toFile();
                    FileInputStream fis = new FileInputStream(fileToZip);
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                    fileToZip.delete();
                }
                zipOut.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setArchive(boolean archive) {
        FileManager.archive = archive;
    }
}
