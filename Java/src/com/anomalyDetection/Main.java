package com.anomalyDetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //String csvName = "a5_Selektion_9000_Daten.csv";
        String csvName = "a5_ohne_Duplikate_final.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        String pathToCsv ="C:/users/rkrys/Downloads/a5.csv/a5.csv"; 
        //String pathToCsv = currentWorkingDir + "/Data/" + csvName;
        //String pathToCsv = currentWorkingDir + "/../Data/" + csvName;
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
        
        //List<Day> wildUnfaelle =new ArrayList<Day>();
        	
        //List<Day> allDays=
        	
//ERMITTEL DEN GOLDEN BATCH        	
       float bestDistance=(float) 0.00; 
       Day bestData=null;//mondays.get(1);//  GOLDENBATCH
       int i=0;
       float totalDistance=(float) 0.00;//for AVGdist
       //Day a= mondays.get(1);
       float sum= (float) 0.00;
       float sumDTW= (float) 0.00;
       for(Day a:mondays) {
    	   sum=(float) 0.00;
    	   sumDTW=(float) 0.00;
        	for(Day b:mondays) {
        		//System.out.println(a.getDTWDistance(a.createCostMatrix(b))+" "+ a.getTotalEuklideanDistanceToDay(b));
        		sum+=a.getTotalEuklideanDistanceToDay(b);
        		sumDTW+=Day.getDTWDistance(a.createCostMatrix(b),23,23);
        		
        		//if(bestDistance==(float)0.00)System.out.println("Yikes  "+sumDTW);
        		if(sumDTW<=bestDistance||bestDistance==(float)0.00) {//sumDTW<=bestDistance||
        			if(bestData!=null) {
        				System.out.println(bestData.getDate() +"____"+bestDistance);
        				Line[] hours=bestData.getHours();
        			       for(int k=0;k<24;k++) {
        			    	   System.out.println(k + "     " +hours[k].getAvgVs() +" km/h");
        			       }
        			}
        			
        			bestDistance=sumDTW;
        			bestData=a;
        			System.out.println("got something better!     "+ a.getDate());
        			System.out.println("NICE:___"+bestDistance);
        		}
        		
        		//i++;
        	}
        	 //System.out.println("BULLSHIT at iteration: "+i +"          "+sumDTW);
//        	 if(sumDTW>(float)16000.00) {//71k                 //FILTER
//        		 System.out.println("STRANGE DATA STRANGE DATA FML");
//        		 Line[] hours=a.getHours();
//        	       for(int k=0;k<24;k++) {
//        	    	   System.out.println(k + "     " +hours[k].getAvgVs() +" km/h" + hours[k].getEventType());
        	       } 
        	       
        	 totalDistance+=sumDTW;
        	i++;

// check all DTW distances to golden batch
        	float allMondaysToGoldenBatchTotal=(float)0.00;
        	for(Day x:mondays) {
        		allMondaysToGoldenBatchTotal+=Day.getDTWDistance(bestData.createCostMatrix(x),23,23);
        	}
        	
        	float allMondaysToGoldenBatchAVG=allMondaysToGoldenBatchTotal/mondays.size();
        	
//Look for Anomalies with higher distances than average      
        	List<Day> anomalies=new ArrayList<Day>();
        	for(Day y:mondays) {
        		if(Day.getDTWDistance(bestData.createCostMatrix(y),23,23)>allMondaysToGoldenBatchAVG) {
        			anomalies.add(y);
        		}
        	}
    
    
       
       System.out.println("FINAL BEST:  "+bestData.getDate()+"     "+bestDistance);
       Line[] hours=bestData.getHours();
       for(int k=0;k<24;k++) {
    	   System.out.println(k + "     " +hours[k].getAvgVs() +" km/h");
       }
       
       
       
        	
        	System.out.println("_________________");
        	System.out.println("AVG Distance to GoldenBatch: "+ allMondaysToGoldenBatchAVG);
        	System.out.println("i: "+i);
        System.out.println(mondays.size());
       // System.out.println(sum);
       // System.out.println(sumDTW);
        System.out.println("________Anomalies_________");
        
        anomalies.stream().map(a->a.getDate()).forEach(System.out::println);
       // System.out.println("avg totaldistance:  "+totalDistance/i);
        //System.out.println(sumDTW);
    }
    
}
