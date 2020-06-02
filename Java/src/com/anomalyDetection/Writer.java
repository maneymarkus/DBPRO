package com.anomalyDetection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

    /**
     * Writes arbitrary content to a new CSV File. The path argument should be relative.
     * @param path      a String containing the relative path to the location where the new csv file should be created
     * @param content   the content which should be written to the new csv file
     */
    public static void writeToCsv(String path, String content) {
        File csvFile = new File(path);
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            pw.println(content);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

}
