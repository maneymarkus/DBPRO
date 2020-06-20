package com.anomalyDetection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeoData {

    //geographical segment based on osm_id
    private final int geoId;
    private ArrayList<Day> days = new ArrayList<>();
    private ArrayList<Week> weeks = new ArrayList<>();
    private ArrayList<Day> anomalies = new ArrayList<>();
    private GoldenBatch goldenBatch;

    public GeoData(Line dataset) {
        this.geoId = dataset.getOsmId();
        this.addData(dataset, true);
    }

    //insert new read line out of .csv in Object Data Structure -> line == hour -> when day of this specific hour has already been created -> add -> otherwise check if week of this hour exist -> if yes, add -> if not, create new Week and new day and add given hour
    public void addData(Line l, boolean learning) {
        Date date = l.getDate();
        //Does the day of the given hour exist?
        if (this.days.stream().anyMatch(d -> d.getDate().compareTo(date) == 0)) {
            //If yes, then add given hour to this day
            this.days.stream().filter(d -> d.getDate().compareTo(date) == 0).limit(1).forEach(d -> d.setHour(l));
        } else {
            //If not, then create a new day with this given hour
            Day newDay = new Day(l);
            this.days.add(newDay);
            //Does the week of the given hour exist?
            if (this.weeks.stream().anyMatch(w -> w.getDayDateStart().compareTo(date) <= 0 && w.getDayDateEnd().compareTo(date) >= 0)) {
                //If yes, then add the new day with the given hour to this week
                this.weeks.stream().filter(w -> w.getDayDateStart().compareTo(date) <= 0 && w.getDayDateEnd().compareTo(date) >= 0).limit(1).forEach(w -> w.setDay(newDay));
            } else {
                //If not, then create a new week and add the new day with the given hour
                this.weeks.add(new Week(newDay, this.geoId));
            }
        }
        if (!learning && this.goldenBatch != null) {
            this.goldenBatch.realtimeComparison(l);
        }
    }

    public int getGeoId() {
        return geoId;
    }

    public ArrayList<Day> getDays() {
        return this.days;
    }

    public ArrayList<Week> getWeeks() {
        return this.weeks;
    }

    public GoldenBatch getGoldenBatch() {
        return this.goldenBatch;
    }

    public void setGoldenBatch(GoldenBatch gB) {
        this.goldenBatch = gB;
    }

    public void findGoldenBatch() {
        this.goldenBatch = GoldenBatch.findOptimalWeek(this.weeks);
    }

    public boolean hasAccident() {
        boolean accident = false;
        for (Week w : weeks) {
            if (w != null) {
                if (w.hasAccident()) {
                    accident = true;
                }
            }
        }
        return accident;
    }

    /**
     *  Probably deprecated. Analyses the whole dataset of this segment and tries to find anomalies within this dataset
     * @return  List of Days which this functions determines to be Anomalies
     */
    public List<Day> findAnomalies() {
        return this.goldenBatch.determineAnomalies(this.weeks);
    }

    public String toCsvString() {
        String weeksString = "";
        for (Week w : this.weeks) {
            if(w != null) {
                weeksString += w.toCsvString();
            } else {
                weeksString += "\n";
            }
        }
        return weeksString;
    }


}
