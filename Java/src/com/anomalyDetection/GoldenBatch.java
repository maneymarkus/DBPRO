package com.anomalyDetection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoldenBatch {

    private final int geoId;
    private ArrayList<GoldenDay> goldenDays;
    private final GoldenWeek goldenWeek;

    public class GoldenDay extends Day {
        public GoldenDay(Day day) {
            super(day.getHour(0));
            Line[] hours = day.getHours();
            for (Line h : hours) {
                this.setHour(h);
            }
        }
    }

    public class GoldenWeek extends Week {
        public GoldenWeek(Week week) {
            super(week.getDayOfWeek(0), geoId);
            Day[] days = week.getDays();
            for (Day d : days) {
                this.setDay(d);
            }
        }
    }

    public GoldenBatch(Week week, int geoId) {
        this.goldenDays = new ArrayList<>();
        for (Day d : week.getDays()) {
            this.goldenDays.add(new GoldenDay(d));
        }
        this.goldenWeek = new GoldenWeek(week);
        this.geoId = geoId;
    }
    
    public void realtimeComparison(Line hour) {
        int weekHour = hour.getWeekHour();
        Line goldenHour = this.goldenWeek.getHourOfWeek(weekHour);
        if(compareHours(hour, goldenHour, 0)) {
            System.out.println("Anomaly! at Segment " + hour.getOsmId() + ". Date and Time: " + hour.getDateTime());
        }
    }
    public static boolean compareHours(Line hourA, Line hourB, float deviation) {
        boolean isAnomaly = false;
        if (Math.abs(hourA.getAvgVs() - hourB.getAvgVs()) > deviation) {
            isAnomaly = true;
        }
        return isAnomaly;
    }

    public GoldenWeek getGoldenWeek() {
        return this.goldenWeek;
    }

    public ArrayList<GoldenDay> getGoldenDays() {
        return this.goldenDays;
    }

    public GoldenDay getGoldenDay(int index) {
        return this.goldenDays.get(index);
    }

    public List<Day> determineAnomalies(List<Week> weeks) {
        List<Day> anomalies = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            List<Day> daysOfWeek = weeks.stream().map(w -> w.getDayOfWeek(finalI)).collect(Collectors.toList());
            float threshold = determineThreshold(daysOfWeek, finalI);
            anomalies.addAll(GoldenBatch.determineAnomaliesDay(this.goldenWeek.getDayOfWeek(i), daysOfWeek, threshold));
        }
        return anomalies;
    }

 
    public float determineThreshold(List<Day> days, int dayOfWeek) {
        float distanceToGoldenBatch = (float)0.00;
        for (Day x : days) {
            if (x == null) {
                continue;
            }
            distanceToGoldenBatch += DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix(this.goldenWeek.getDayOfWeek(dayOfWeek), x),23,23);
        }
        float distanceToGoldenBatchAvg = distanceToGoldenBatch / days.size();
        return distanceToGoldenBatchAvg;
    }
    
    //determine threshold per hour
    public float determineThresholdpH(List<Day> days, int dayOfWeek,int h) {
        float distanceToGoldenBatch = (float)0.00;
        for (Day x : days) {
            if (x == null) {
                continue;
            }
            distanceToGoldenBatch += DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix(this.goldenWeek.getDayOfWeek(dayOfWeek), x),h,h);
        }
        float distanceToGoldenBatchAvg = distanceToGoldenBatch / days.size();
        return distanceToGoldenBatchAvg;
    }
    //determine threshold per hour EUK
    public float determineEUKThresholdpH(List<Day> days, int dayOfWeek,int h) {
        float distanceToGoldenBatch = (float)0.00;
        for (Day x : days) {
            if (x == null) {
                continue;
            }
            distanceToGoldenBatch += DistanceUtility.getEuklideanDistanceToDayAtH(this.goldenWeek.getDayOfWeek(dayOfWeek), x, h);
            		//DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix(this.goldenWeek.getDayOfWeek(dayOfWeek), x),h,h);
        }
        float distanceToGoldenBatchAvg = distanceToGoldenBatch / days.size();
        return distanceToGoldenBatchAvg;
    }
    
    public float findMaxDistance(List<Day> days, int dayOfWeek, int h) {
        float distanceToGoldenBatch = (float)0.00;
        for (Day x : days) {
            if (x == null) {
                continue;
            }
            if (distanceToGoldenBatch<=DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix(this.goldenWeek.getDayOfWeek(dayOfWeek), x),h,h)){
            	distanceToGoldenBatch=DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix(this.goldenWeek.getDayOfWeek(dayOfWeek), x),h,h);
            }
        }
        float distanceToGoldenBatchAvg = distanceToGoldenBatch;/// days.size();
        return distanceToGoldenBatchAvg;
    }

    /*
     * STATIC ANALYSE FUNCTIONS
     */

    public static GoldenBatch findOptimalWeek(List<Week> weeks) {
        Week optimalWeek = null;
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            List<Day> daysOfWeek = weeks.stream().map(w -> w.getDayOfWeek(finalI)).collect(Collectors.toList());
            Day optimalDay = findOptimalDay(daysOfWeek);
            if (optimalWeek == null) {
                optimalWeek = new Week(optimalDay, weeks.get(0).getGeoId());
            } else {
                optimalWeek.setDay(optimalDay);
            }
        }
        return new GoldenBatch(optimalWeek, optimalWeek.getGeoId());
    }

    public static Day findOptimalDay(List<Day> days) {
        //ERMITTEL DEN GOLDEN BATCH
        float bestDistance = (float) 0.00;
        Day bestData = null; // GOLDENBATCH
        int i = 0;
      //  float totalDistance = (float) 0.00;//for AVGdist
      //  float sum = (float) 0.00;
        float sumDTW = (float) 0.00;
        for(Day a : days) {
            if (a == null) {
                continue;
            }
          //  sum = (float) 0.00;
            sumDTW = (float) 0.00;
            for (Day b : days) {
                if (b == null) {
                    continue;
                }
                ////System.out.println(a.getDTWDistance(a.createCostMatrix(b))+" "+ a.getTotalEuklideanDistanceToDay(b));
               // sum += DistanceUtility.getTotalEuklideanDistanceToDay(a, b);
                sumDTW += DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix(a, b), 23, 23);

               // if(bestDistance==(float)0.00)System.out.println("Yikes  "+sumDTW);
                if (sumDTW <= bestDistance || bestDistance == (float) 0.00) {//sumDTW<=bestDistance||
                    if (bestData != null) {
                       // System.out.println(bestData.getDate() + "____" + bestDistance);
                        Line[] hours = bestData.getHours();
                        for (int k = 0; k < 24; k++) {
                           // System.out.println(k + "     " + hours[k].getAvgVs() + " km/h");
                        }
                    }
                    bestDistance = sumDTW;
                    bestData = a;
                   // System.out.println("got something better!     " + a.getDate());
                   // System.out.println("NICE:___" + bestDistance);
                }
            }
        }
//        if (bestData != null) {
//            System.out.println(bestData.getDate() + "___OPTI_" + bestDistance);
//            Line[] hours = bestData.getHours();
//            for (int k = 0; k < 24; k++) {
//                System.out.println(k + "     " + hours[k].getAvgVs() + " km/h");
//            }
//        }
        return bestData;
    }

    public static List<Day> determineAnomaliesDay(Day goldenBatchDay, List<Day> days, float threshold) {
        List<Day> anomalies = new ArrayList<Day>();
        for (Day x : days) {
            if (x == null) {
                continue;
            }
            if (DistanceUtility.getDTWDistance(DistanceUtility.createCostMatrix(goldenBatchDay, x),23,23) > (threshold * 2)) {
                anomalies.add(x);
            }
        }
        return anomalies;
    }

}
