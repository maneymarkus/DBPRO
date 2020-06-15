package com.anomalyDetection;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //String csvName = "a5_Selektion_9000_Daten.csv";
        String csvName = "a5_ohne_Duplikate_final.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        //String pathToCsv ="C:/users/rkrys/Downloads/a5.csv/a5.csv";
        String pathToCsv = currentWorkingDir + "/../Data/" + csvName;
        Data data = new Data();
        Reader reader = new Reader(pathToCsv, data);

        //reader.readSomeLines();
        reader.readLinesToObjects();

        System.out.println(data.getSegments().size());
        System.exit(0);

        GeoData firstSegment = data.getSegments().get(0);

        firstSegment.findGoldenBatch();

        System.out.println(firstSegment.getGoldenBatch().getGoldenDay(0).getDate());

    }
    
}
