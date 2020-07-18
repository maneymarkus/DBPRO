package com.anomalyDetection;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		// String csvName = "a5_Selektion_9000_Daten.csv";
		String csvName = "a5_ohne_Duplikate_final.csv";
		String currentWorkingDir = System.getProperty("user.dir");
		String pathToCsv = "C:/users/rkrys/Downloads/a5.csv/a5.csv"; // needs to be changed
		// String pathToCsv = currentWorkingDir + "/../Data/" + csvName;
		Data data = new Data();
		Reader reader = new Reader(pathToCsv, data);
		String outPath = "C:/users/rkrys/Downloads"; // needs to be changed

		// reader.readSomeLines();
		reader.readLinesToObjects();

		GeoData[] segs = new GeoData[21];

		for (int i = 0; i < 21; i++) {
			segs[i] = data.getSegments().get(i);
		}
		for (int i = 0; i < 21; i++) {
			System.out.println(segs[i].getGeoId());
		}

		ExecutorService executorService = Executors.newCachedThreadPool();

//EUCLIDEAN:       
		// 0
		// for (int i = 0; i<21; i++) {
		executorService.execute(() -> {
			System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
			float thr = segs[0].findAVGThreshold();
			if (thr != (float) 0.00) {
				List<Day> d = segs[0].findAnomaliesByThr(thr);
				String csv = "";
				Day[] last = new Day[7];
				while (!d.isEmpty()) {
					for (int i = 0; i < 7; i++) {
						final int j = i;
						Day x = d.stream().filter(s -> s.getDayOfWeek() == j).findFirst().orElse(last[j]);
						if (x == null)
							break;
						csv += x.toCsvString();
						last[j] = x;
						d.remove(x);
					}
				}
				Writer.writeToCsv(outPath + "/ANOMALIES_" + segs[0].getGeoId() + ".csv", csv);
				// System.out.println("SEGMENT: "+segs[0].getGeoId()+ " Grösse: " +
				// segs[0].getDays().size()+ " Falsche Tage: "+ d.size());
			}
		});

		
		
		// 0 DTW DISTANCE
		// for (int i = 0; i<21; i++) {
		executorService.execute(() -> {
			System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
			float thr = segs[0].findAVGThresholdDTW();
			if (thr != (float) 0.00) {
				List<Day> d = segs[0].findAnomaliesByThrDTW(thr);
				String csv = "";
				Day[] last = new Day[7];
				while (!d.isEmpty()) {
					for (int i = 0; i < 7; i++) {
						final int j = i;
						Day x = d.stream().filter(s -> s.getDayOfWeek() == j).findFirst().orElse(last[j]);
						if (x == null)
							break;
						csv += x.toCsvString();
						last[j] = x;
						d.remove(x);
					}
				}
				Writer.writeToCsv(outPath + "/ANOMALIES_DTW_" + segs[0].getGeoId() + ".csv", csv);

				
				
				// GB
				for (int k = 0; k < 21; k++) {
					GeoData seg = segs[k];
					seg.findGoldenBatch();
					Line[][] week = new Line[24][7];
					String vektor = "";

					for (int i = 0; i < 7; i++) {
						week[i] = seg.getGoldenBatch().getGoldenDay(i).getHours();
						for (int j = 0; j < 24; j++) {
							vektor += week[i][j].getAvgVs() + "\n";
						}
					}
					// OUT "C:/users/rkrys/Downloads/GB_"+segment.getGeoId()+".csv"

					try {
						PrintWriter out = new PrintWriter("C:/users/rkrys/Downloads/GB_" + seg.getGeoId() + ".txt");
						out.println(vektor);
						out.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			    
				
				
				//tons of Executor services 
 /*  	 
    	 //1 EUCLIDEAN DISTANCE:
  //  }
 
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[1];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//2
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[2];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//3
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[3];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//4
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[4];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//5
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[5];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//6
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[6];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//7
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[7];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//8
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[8];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//9
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[9];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//10
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[10];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//11
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[11];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//12
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[12];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//13
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[13];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//14
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[14];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//15
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[15];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//16
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[16];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//17
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[17];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//18
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[18];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//19
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[19];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//20
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[20];
    		 float thr=segment.findAVGThreshold();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThr(thr);
    			 String csv="";
//    			 for(Day day:d) {
//    				csv+=day.toCsvString();
//    			 }
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});
    */	
    
    
    
  // DTW_______________________DTW________________________DTW
    
    //DTW EXECUTOR SERVICES:
    
//    //0
//   // for (int i = 0; i<21; i++) {
//    	 executorService.execute(()->{
//    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
//    		 float thr=segs[0].findAVGThresholdDTW();
//    		 if(thr!=(float)0.00) {
//    			 List<Day> d=segs[0].findAnomaliesByThrDTW(thr);
//    			 String csv="";
//    			 Day[] last= new Day[7];
//    			 while(!d.isEmpty()) {
//    			 for(int i=0;i<7;i++) {
//    				 final int j =i;
//    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
//    				 if (x==null) break;
//    				 csv+=x.toCsvString();
//    				 last[j]=x;
//    				 d.remove(x);
//    			 }
//    			 }
//    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segs[0].getGeoId()+".csv", csv);
    	//	System.out.println(segs[i].getGeoId());
       // System.out.println(segs[i].getDays().size());
       	System.out.println("SEGMENT: "+segs[0].getGeoId()+ " Grösse: " + segs[0].getDays().size()+ " Falsche Tage: "+ d.size());
      // d.stream().map(s->s.getDate()).forEach(System.out::println);
    		 }
    	});
    	 
    	 /*
    	 //1
  //  }
 
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[1];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//2
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[2];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//3
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[3];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//4
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[4];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//5
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[5];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//6
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[6];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//7
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[7];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//8
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[8];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//9
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[9];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//10
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[10];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//11
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[11];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//12
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[12];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//13
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[13];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//14
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[14];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//15
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[15];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//16
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[16];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//17
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[17];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//18
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[18];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//19
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[19];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});//20
    	 executorService.execute(()->{
    		 System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
    		 GeoData segment=segs[20];
    		 float thr=segment.findAVGThresholdDTW();
    		 if(thr!=(float)0.00) {
    			 List<Day> d=segment.findAnomaliesByThrDTW(thr);
    			 String csv="";
//    			 for(Day day:d) {
//    				csv+=day.toCsvString();
//    			 }
    			 Day[] last= new Day[7];
    			 while(!d.isEmpty()) {
    			 for(int i=0;i<7;i++) {
    				 final int j =i;
    				 Day x = d.stream().filter(s->s.getDayOfWeek()==j).findFirst().orElse(last[j]);
    				 if (x==null) break;
    				 csv+=x.toCsvString();
    				 last[j]=x;
    				 d.remove(x);
    			 }
    			 }
    			 Writer.writeToCsv(outPath+"/ANOMALIES_DTW_"+segment.getGeoId()+".csv", csv);
    			 //Writer.writeDaysToCsv("C:/users/rkrys/Downloads/ANOMALIES_DTW_"+segs[1].getGeoId()+".csv", d);
       	System.out.println("SEGMENT: "+segment.getGeoId()+ " Grösse: " + segment.getDays().size()+ " Falsche Tage: "+ d.size());
    		 }
    	});
    
    */
    
    
//    //GB+THRESH
//    for(int k=0;k<21;k++) {
//    	GeoData seg=segs[k];
//        seg.findGoldenBatch();
//    	Line[][] week=new Line[24][7];
//        String vektor="";
//        
//        for(int i=0; i<7;i++) {
//        	week[i]=seg.getGoldenBatch().getGoldenDay(i).getHours();
//        	for(int j=0;j<24;j++) {
//        		vektor+=week[i][j].getAvgVs() + seg.getAVGDistance(i, j)*seg.thresh +"\n";
//        	}
//        }
//        //OUT "C:/users/rkrys/Downloads/GB_"+segment.getGeoId()+".csv"
//        
//        try {
//			PrintWriter out = new PrintWriter("C:/users/rkrys/Downloads/GB_plus_THRESH_"+seg.getGeoId()+".txt");
//			 out.println(vektor);
//			 out.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    }
//    
//    //GB/THRESH
//    for(int k=0;k<21;k++) {
//    	GeoData seg=segs[k];
//        seg.findGoldenBatch();
//    	Line[][] week=new Line[24][7];
//        String vektor="";
//        
//        for(int i=0; i<7;i++) {
//        	week[i]=seg.getGoldenBatch().getGoldenDay(i).getHours();
//        	for(int j=0;j<24;j++) {
//        		vektor+=week[i][j].getAvgVs()  - seg.getAVGDistance(i, j)*seg.thresh +"\n";
//        	}
//        }
//        //OUT "C:/users/rkrys/Downloads/GB_"+segment.getGeoId()+".csv"
//        
//        try {
//			PrintWriter out = new PrintWriter("C:/users/rkrys/Downloads/GB_minus_THRESH_"+seg.getGeoId()+".txt");
//			 out.println(vektor);
//			 out.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//    }
    	 
    	 
    

}
  
    
}
