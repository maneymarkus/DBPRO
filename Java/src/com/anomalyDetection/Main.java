package com.anomalyDetection;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvName = "a5_ohne_Duplikate_final.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        //String pathToCsv ="C:/users/rkrys/Downloads/a5.csv/a5.csv"; 
        String pathToCsv =currentWorkingDir + "/Data/" + csvName;
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
        
        so.forEach(Segment::sortByHour);
        so.forEach(Segment::evaluateSegmentbyHour);
        
        
        so.forEach(s-> {
        	for(int i=0; i<730; i++) {
        		for(int h=0; h<24;h++) {
        			if(s.getFavorite(i, h)!=null)System.out.println("Day:"+i + "  Hour:"+h +"     "+s.getFavoriteDistance(i,h) +"     "+ s.getFavorite(i, h).getAvgVs());
        		}
        	}
        });
       
        
        
        
    }
}
