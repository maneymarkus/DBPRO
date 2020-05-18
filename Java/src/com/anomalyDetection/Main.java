package com.anomalyDetection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //String csvName = "a5_Selektion_9000_Daten.csv";
        String csvName = "a5_ohne_Duplikate_final.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        //String pathToCsv ="C:/users/rkrys/Downloads/a5.csv/a5.csv"; 
        //String pathToCsv = currentWorkingDir + "/Data/" + csvName;
        String pathToCsv = currentWorkingDir + "/../Data/" + csvName;
        //System.out.println(pathToCsv);
        Data data = new Data();
        Reader reader = new Reader(pathToCsv, data);

        //reader.readSomeLines();
        reader.readLinesToObjects();

        data.getSegments().get(0).getWeeks().stream().forEach(w -> {
            if (w.getDayOfWeek(0) != null) {
                System.out.println(w.getDayOfWeek(0).getHour(0).getAvgVs());
            }
        });

        List<Line> readLines = data.getLines();
        //System.out.println(readLines.get(450000).getDateTime());
        /*
        Sorter.sortLines(readLines);
        
        List<Segment> so = Sorter.getSegments();

        Sorter.getSegments().stream().forEach(s -> {
            System.out.println(s.getId() + ": " + s.getData().size());
        });
         */

        /*

        so.forEach(Segment::sortByHour);
        so.forEach(Segment::evaluateSegmentbyHour);
        
        
        so.forEach(s-> {
        	for(int i=0; i<730; i++) {
        		for(int h=0; h<24;h++) {
        			if(s.getFavorite(i, h)!=null)System.out.println("Day:" + i + "  Hour:"+ h + "     " + s.getFavoriteDistance(i,h) + "     " + s.getFavorite(i, h).getAvgVs());
        		}
        	}
        });
       
        */

        /*
        TEST

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dayDate = "2020-05-11";

        try {
            Date date = dateFormat.parse(dayDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // sets Calender to Sunday of the week of the given date
            calendar.set(calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
            // sets Calender to Monday == start of week
            calendar.roll(calendar.DAY_OF_WEEK, -6);
            Date dayDateStart = calendar.getTime();
            System.out.println(date.compareTo(dayDateStart));
        } catch (Exception e) {
            System.out.println(e);
        }
        */
        
    }
}
