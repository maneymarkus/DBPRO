package com.anomalyDetection;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvName = "a5_ohne_Duplikate.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        String pathToCsv = currentWorkingDir + "/../Data/" + csvName;
        //String pathToCsv = currentWorkingDir + "/Data/" + csvName;
        System.out.println(pathToCsv);
        Reader reader = new Reader(pathToCsv);
        //reader.readSomeLines();
        List<Line> readLines = reader.readLinesToObjects();
        //System.out.println(readLines.get(450000).getDateTime());
        Sorter.sortLines(readLines);

        Sorter.getSegments().stream().forEach(s -> {
            System.out.println(s.getId() + ": " + s.getData().size());
        });
        
    }
}
