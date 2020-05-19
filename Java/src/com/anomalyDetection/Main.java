package com.anomalyDetection;

import java.util.List;
import java.util.stream.Collectors;

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

//        data.getSegments().get(0).getWeeks().stream().forEach(w -> {
//            if (w.getDayOfWeek(0) != null) {
//                System.out.println(w.getDayOfWeek(0).getHour(0).getAvgVs());
//            }
//        });
        List<Day> mondays=data.getSegments().get(0).getWeeks().stream().filter( s->s.getDayOfWeek(0)!=null).map(s->s.getDayOfWeek(0)).collect(Collectors.toList());
       int i=0;
        //for(Day a:mondays) {
       Day a= mondays.get(1);
       float sum= (float) 0.00;
       float sumDTW= (float) 0.00;
        	for(Day b:mondays) {
        		System.out.println(a.getDTWDistance(a.createCostMatrix(b))+" "+ a.getTotalEuklideanDistanceToDay(b));
        		sum+=a.getTotalEuklideanDistanceToDay(b);
        		sumDTW+=a.getDTWDistance(a.createCostMatrix(b));
        		i++;
        	}
        //}
        System.out.println(i);
        System.out.println(mondays.size());
        System.out.println(sum);
        System.out.println(sumDTW);
        
    }
}
