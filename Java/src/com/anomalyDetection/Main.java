package com.anomalyDetection;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvName = "a5_ohne_Duplikate_final.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        String pathToCsv = currentWorkingDir + "/Data/" + csvName;
        //String pathToCsv = currentWorkingDir + "/Data/" + csvName;
        System.out.println(pathToCsv);
        Reader reader = new Reader(pathToCsv);
        //reader.readSomeLines();
        List<Line> readLines = reader.readLinesToObjects();
        //System.out.println(readLines.get(450000).getDateTime());
        Sorter.sortLines(readLines);
        
        List<Segment> so= Sorter.getSegments();

        Sorter.getSegments().stream().forEach(s -> {
            System.out.println(s.getId() + ": " + s.getData().size());
        });
        
        so.forEach(Segment::sortByDay);
        so.forEach(Segment::evaluateSegmentbyDay);
        
        
        so.stream().forEach(s-> {
        	for(int i=0; i<730; i++) {
        		System.out.println("Day:"+i +"     "+s.getFavoriteDistance(i));
        	}
        });
       
        
        
        
    }
}
