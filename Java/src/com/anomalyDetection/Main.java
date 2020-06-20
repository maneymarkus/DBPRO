package com.anomalyDetection;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //String csvName = "a5_Selektion_9000_Daten.csv";
        String csvName = "a5_ohne_Duplikate_final.csv";
        String currentWorkingDir = System.getProperty("user.dir");
        String pathToCsv ="C:/users/rkrys/Downloads/a5.csv/a5.csv";
        //String pathToCsv = currentWorkingDir + "/../Data/" + csvName;
        Data data = new Data();
        Reader reader = new Reader(pathToCsv, data);

        //reader.readSomeLines();
        reader.readLinesToObjects();

//        GeoData firstSegment = data.getSegments().get(0);
//        GeoData secondSegment = data.getSegments().get(1); //3234782
//        GeoData thirdSegment = data.getSegments().get(2);
//        GeoData fourthSegment = data.getSegments().get(3);
//        GeoData fifthSegment = data.getSegments().get(4);
//        GeoData sixthSegment = data.getSegments().get(5);
//        GeoData seventhSegment = data.getSegments().get(6);
//        GeoData eigthSegment = data.getSegments().get(7);
//        GeoData twentyfirstSegment = data.getSegments().get(20);
//        
        GeoData[] segs=new GeoData[21];
        
        for (int i = 0; i<21; i++) {
        	segs[i]=data.getSegments().get(i);
        }

//        System.out.println("1: "+firstSegment.getGeoId());
//        System.out.println("2: "+secondSegment.getGeoId());
//        System.out.println("3: "+thirdSegment.getGeoId());
//        System.out.println("4: "+fourthSegment.getGeoId());
//        System.out.println("5: "+fifthSegment.getGeoId());
//        System.out.println("6: "+sixthSegment.getGeoId());
//        System.out.println("7: "+seventhSegment.getGeoId());
//        System.out.println("8: "+eigthSegment.getGeoId());
//        System.out.println("21: "+twentyfirstSegment.getGeoId());
//        List<Day> ds=segs[1].findAnomaliesByThr(segs[1].findMinThreshold());
//        
//        System.out.println(segs[1].getGeoId());
//        System.out.println(segs[1].getDays().size());
//        System.out.println(ds.size());
//        ds.stream().map(s->s.getDate()).forEach(System.out::println);
//        
        ExecutorService executorService = Executors.newCachedThreadPool();
        	//tonnenweise Executor services ... können ignoriert werden ^^
        
        
       // for (int i = 0; i<21; i++) {
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 //float thr=
        		 if(segs[0].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[0].findAnomaliesByThr(segs[0].findMinThreshold());
        	//	System.out.println(segs[i].getGeoId());
           // System.out.println(segs[i].getDays().size());
           	System.out.println("SEGMENT: "+segs[0].getGeoId()+ " Grösse: " + segs[0].getDays().size()+ " Falsche Tage: "+ d.size());
          // d.stream().map(s->s.getDate()).forEach(System.out::println);
        		 }
        	});
      //  }
     
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[1].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[1].findAnomaliesByThr(segs[1].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[1].getGeoId()+ " Grösse: " + segs[1].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});
        	 //2
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[2].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[2].findAnomaliesByThr(segs[2].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[2].getGeoId()+ " Grösse: " + segs[2].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//3
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[3].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[3].findAnomaliesByThr(segs[3].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[3].getGeoId()+ " Grösse: " + segs[3].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//4
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[4].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[4].findAnomaliesByThr(segs[4].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[4].getGeoId()+ " Grösse: " + segs[4].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//5
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[5].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[5].findAnomaliesByThr(segs[5].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[5].getGeoId()+ " Grösse: " + segs[5].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//6
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[6].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[6].findAnomaliesByThr(segs[6].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[6].getGeoId()+ " Grösse: " + segs[6].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//7
        	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 if(segs[7].findMinThreshold()!=(float)0.00) {
    			 List<Day> d=segs[7].findAnomaliesByThr(segs[7].findMinThreshold());
       	System.out.println("SEGMENT: "+segs[7].getGeoId()+ " Grösse: " + segs[7].getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//8
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[8].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[8].findAnomaliesByThr(segs[8].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[8].getGeoId()+ " Grösse: " + segs[8].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//9
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[9].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[9].findAnomaliesByThr(segs[9].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[9].getGeoId()+ " Grösse: " + segs[9].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//10
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[10].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[10].findAnomaliesByThr(segs[10].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[10].getGeoId()+ " Grösse: " + segs[10].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//11
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[11].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[11].findAnomaliesByThr(segs[11].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[11].getGeoId()+ " Grösse: " + segs[11].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//12
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[12].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[12].findAnomaliesByThr(segs[12].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[12].getGeoId()+ " Grösse: " + segs[12].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//13
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[13].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[13].findAnomaliesByThr(segs[13].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[13].getGeoId()+ " Grösse: " + segs[13].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//14
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[14].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[14].findAnomaliesByThr(segs[14].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[14].getGeoId()+ " Grösse: " + segs[14].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//15
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[15].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[15].findAnomaliesByThr(segs[15].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[15].getGeoId()+ " Grösse: " + segs[15].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//16
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[16].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[16].findAnomaliesByThr(segs[16].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[16].getGeoId()+ " Grösse: " + segs[16].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//17
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[17].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[17].findAnomaliesByThr(segs[17].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[17].getGeoId()+ " Grösse: " + segs[17].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//18
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[18].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[18].findAnomaliesByThr(segs[18].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[18].getGeoId()+ " Grösse: " + segs[18].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//19
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[19].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[19].findAnomaliesByThr(segs[19].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[19].getGeoId()+ " Grösse: " + segs[19].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});//20
        	 executorService.execute(()->{
        		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
        		 if(segs[20].findMinThreshold()!=(float)0.00) {
        			 List<Day> d=segs[20].findAnomaliesByThr(segs[20].findMinThreshold());
           	System.out.println("SEGMENT: "+segs[20].getGeoId()+ " Grösse: " + segs[20].getDays().size()+ " Falsche Tage: "+ d.size());
        		 }
        	});
        
        
        
        //secondSegment.getDays().stream().filter(s->s.hasAccident()&& !s.equals(null)).forEach(s-> System.out.println("ACCIDENT: "+ s.getDate()));

       // secondSegment.findGoldenBatch();

//        System.out.println("GB: "+secondSegment.getGoldenBatch().getGoldenDay(0).getDate());
//        //System.out.println("Threshold: "+ secondSegment.getGoldenBatch().determineThreshold(secondSegment.getDays(), 0) +"");
//        System.out.println("Threshold at hour 6 "+ secondSegment.getGoldenBatch().determineThresholdpH(secondSegment.getDays(), 0,6) +"");
//        //System.out.println("Threshold at hour 7 "+ secondSegment.getGoldenBatch().determineThresholdpH(secondSegment.getDays(), 0,7) +"");
//        //System.out.println("Threshold at hour 7 "+ secondSegment.getGoldenBatch().determineThresholdpH(secondSegment.getDays(), 0,8) +"");
//        System.out.println("MAXThreshold at hour 6 "+ secondSegment.getGoldenBatch().findMaxDistance(secondSegment.getDays(), 0,6) +"");
//        
//        Optional<Day> err =secondSegment.getDays().stream().filter(s->s.hasAccident()&& !s.equals(null)).findFirst();
//        
//        if(err.isPresent()) {
//        	System.out.println("ACCIDENT: "+ err.get().getDate());
//        	
//        	//System.out.println("Distance between GB and ACC per day: "+ DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix((Day)secondSegment.getGoldenBatch().getGoldenDay(0), err.get()),23, 23));
//        	System.out.println("Distance between GB and ACC in hour X: "+ DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix((Day)secondSegment.getGoldenBatch().getGoldenDay(0), err.get()), 6, 6));
//        	//System.out.println("Distance between GB and ACC in hour X+1: "+ DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix((Day)secondSegment.getGoldenBatch().getGoldenDay(0), err.get()), 7, 7));
//        	//System.out.println("Distance between GB and ACC in hour X+2: "+ DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix((Day)secondSegment.getGoldenBatch().getGoldenDay(0), err.get()), 8, 8));
//        }

    }
    
}
