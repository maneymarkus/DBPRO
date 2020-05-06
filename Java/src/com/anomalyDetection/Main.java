package com.anomalyDetection;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvName = "a5.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        String pathToCsv = currentWorkingDir + "/../" + csvName;
        Reader reader = new Reader(pathToCsv);
        //reader.readSomeLines();
        List<Line> readLines = reader.readLinesToObjects();
        System.out.println(readLines.get(450000).getDateTime());
    }
}
