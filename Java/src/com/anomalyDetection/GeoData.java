package com.anomalyDetection;

import java.util.ArrayList;
import java.util.Date;

public class GeoData {

    //geographical segment based on osm_id
    private final int geoId;
    private ArrayList<Day> days = new ArrayList<>();
    private ArrayList<Week> weeks = new ArrayList<>();

    public GeoData(Line dataset) {
        this.geoId = dataset.getOsmId();
        this.addData(dataset);
    }

    //insert new read line out of .csv in Object Data Structure -> line == hour -> when day of this specific hour has already been created -> add -> otherwise check if week of this hour exist -> if yes, add -> if not, create new Week and new day and add given hour
    public void addData(Line l) {
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
                this.weeks.add(new Week(newDay));
            }
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


}
