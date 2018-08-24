package main.java.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {

    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    private static String inputFolder;
    private static String outputFolder;
    private static String tempFolder;
    private static String logsArchiveFolder;
    private static String companyBigLogoAddress;
    private static String companySmallLogoAddress;


    static {
        FileInputStream fileInputStream;
        Properties prop = new Properties();
        try {

            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            inputFolder = prop.getProperty("inputFolder");
            outputFolder = prop.getProperty("outputFolder");
            tempFolder = prop.getProperty("tempFolder");
            logsArchiveFolder = prop.getProperty("archiveFolder");
            companyBigLogoAddress = prop.getProperty("companyBigLogoAddress");
            companySmallLogoAddress = prop.getProperty("companySmallLogoAddress");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getInputFolder() {
        return inputFolder;
    }

    public static String getOutputFolder() {
        return outputFolder;
    }

    public static String getTempFolder() {
        return tempFolder;
    }

    public static String getLogsArchiveFolder() {
        return logsArchiveFolder;
    }

    public static String getCompanyBigLogoAddress() {
        return companyBigLogoAddress;
    }

    public static String getCompanySmallLogoAddress() {
        return companySmallLogoAddress;
    }
}
